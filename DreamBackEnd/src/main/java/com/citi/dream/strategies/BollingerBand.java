package com.citi.dream.strategies;

import com.citi.dream.jms.MessageSender;
import com.citi.dream.jms.Order;
import org.json.JSONArray;
import org.json.JSONException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="bollinger_band")

public class BollingerBand implements Strategy, Serializable {

    @Id
    @Column(name= "strategyID")
    private String strategyID;
    @Column(name="type") private String type;
    @Column(name="duration_time") private int durationTime;
    @Column(name="num_of_stddev") private double numOfStddev = 2;
    @Column(name="stock_name")private String stockName;
    @Column(name="volume")private int volume;
    @Column(name="cut_off_percentage") private double cutOffPercentage; //the cutoff that stops the
    @Column(name="buying")private boolean buying; //true if buying, false if selling
    @Column(name="state") private String state; // state of the strategy: running, paused, stopped

    @Transient
    private boolean openPosition = false;
    @Transient private double executedOrderPrice = -1;

    @Column(name="profit") private double profit;

    @OneToMany(mappedBy="bollingerBand", cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @Transient transient List<Order> orderList = new ArrayList<>();
    @Transient private transient PriceGetter priceGetter;
    @Transient private transient MessageSender messageSender;

    public BollingerBand(String type, int durationTime, String stockName, int volume,
                         String strategyID, double cutOffPercentage, PriceGetter priceGetter,
                         MessageSender messageSender) {
        this.strategyID = strategyID;
        this.type = type;
        this.durationTime = durationTime;
        this.cutOffPercentage = cutOffPercentage;
        this.stockName = stockName;
        this.volume = volume;
        this.priceGetter = priceGetter;
        this.messageSender = messageSender;
        this.state = "running";
    }

    public void addOrder(Order o) {
        orderList.add(o);
        o.setBollingerBand(this);
    }

    public String getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(String strategyID) {
        this.strategyID = strategyID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public double getNumOfStddev() {
        return numOfStddev;
    }

    public void setNumOfStddev(double numOfStddev) {
        this.numOfStddev = numOfStddev;
    }

    public double getCutOffPercentage() {
        return cutOffPercentage;
    }

    public void setCutOffPercentage(double cutOffPercentage) {
        this.cutOffPercentage = cutOffPercentage;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isBuying() {
        return buying;
    }

    public void setBuying(boolean buying) {
        this.buying = buying;
    }

    public boolean isOpenPosition() {
        return openPosition;
    }

    public void setOpenPosition(boolean openPosition) {
        this.openPosition = openPosition;
    }

    public double getExecutedOrderPrice() {
        return executedOrderPrice;
    }

    public void setExecutedOrderPrice(double executedOrderPrice) {
        this.executedOrderPrice = executedOrderPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public PriceGetter getPriceGetter() {
        return priceGetter;
    }

    public void setPriceGetter(PriceGetter priceGetter) {
        this.priceGetter = priceGetter;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    public BollingerBand() {
    } //Need it for JPA


    //copied from TwoMovingAverages, might make less redundant later
    public double calculateAverage(int period) throws JSONException {

        JSONArray result = priceGetter.getStockData().get(this.stockName);
//        System.out.println("result is:::");
//        System.out.println(result);
        double sum = 0;
        if (result != null) {
            for (int i = 0; i < period && i < result.length(); i++) {
                sum += Double.parseDouble(result.getJSONObject(i).getString("price"));
            }
        }

        return sum / period;
    }


    public double calculateStddev(int period) throws JSONException {

        double avg = calculateAverage(period);
        double stddev = -1;
        double sum = 0;
        JSONArray result = priceGetter.getStockData().get(this.stockName);


//        System.out.println("result is:::");
//        System.out.println(result);
        if (result != null) {
            double squareDev;
            for (int i = 0; i < period && i < result.length(); i++) {
                squareDev =
                        Math.pow(Double.parseDouble(result.getJSONObject(i).getString("price"))-avg, 2);
                sum += squareDev;
            }
        }


        stddev = Math.sqrt(sum/period);
//        System.out.println("sum: ");
//        System.out.println(sum);
//        System.out.println("dev: ");
//        System.out.println(stddev);
        return stddev;
    }

    public void performStrategy() throws JSONException {
        System.out.println("in boll perf strat");
        System.out.println("durationTime");
        System.out.println(durationTime);
        this.priceGetter.setStockName(this.stockName);
        this.priceGetter.setNumOfStocks(this.durationTime);


        double currentPrice = -1.0;

        JSONArray currentStockPrice = this.priceGetter.getStockData().get(stockName);
        if (currentStockPrice != null && currentStockPrice.length() > 0) {
            currentPrice = Double.parseDouble(currentStockPrice.getJSONObject(0).getString("price"));
        }
        System.out.println("current avg: ");
        System.out.println(calculateAverage(durationTime));


        System.out.println("current stddev: ");
        System.out.println(calculateStddev(durationTime));






    }


}
