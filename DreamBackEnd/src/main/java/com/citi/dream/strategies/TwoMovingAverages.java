package com.citi.dream.strategies;


import com.citi.dream.jms.MessageSender;
import com.citi.dream.jms.Order;
import org.aspectj.weaver.ast.Or;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="two_moving_averages")

public class TwoMovingAverages implements Strategy, Serializable {

    @Id
    @Column(name= "strategyID")
    private String strategyID;

    @Column(name="type") private String type; //two moving averages
    @Column(name="long_time")private int longTime;
    @Column(name="short_time")private int shortTime;
    @Column(name="stock_name")private String stockName;
    @Column(name="volume")private int volume;
    @Column(name="cut_off_percentage") private double cutOffPercentage; //the cutoff that stops the
    // strategy
    @Column(name="action")private String action; //buy or sell
    @Column(name="buying")private boolean buying; //true if buying, false if selling

    @Column(name="delta") private double delta = 0.02;

    @OneToMany(mappedBy="twoMovingAverages", cascade={CascadeType.MERGE, CascadeType.PERSIST})
    private transient List<Order> orderList = new ArrayList<>();

    @Transient
    private transient PriceGetter priceGetter;
    @Transient
    private transient MessageSender messageSender;

    public void addOrder(Order o) {
        orderList.add(o);
        o.setTwoMovingAverages(this);
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLongTime() {
        return longTime;
    }

    public void setLongTime(int longTime) {
        this.longTime = longTime;
    }

    public int getShortTime() {
        return shortTime;
    }

    public void setShortTime(int shortTime) {
        this.shortTime = shortTime;
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

    public String getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(String strategyID) {
        this.strategyID = strategyID;
    }

    public double getCutOffPercentage() {
        return cutOffPercentage;
    }

    public void setCutOffPercentage(double cutOffPercentage) {
        this.cutOffPercentage = cutOffPercentage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PriceGetter getPriceGetter() {
        return priceGetter;
    }

    public void setPriceGetter(PriceGetter priceGetter) {
        this.priceGetter = priceGetter;
    }

    public boolean isBuying() {
        return buying;
    }

    public void setBuying(boolean buying) {
        this.buying = buying;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public TwoMovingAverages(String type, int longTime, int shortTime, String stockName, int volume, String strategyID, double cutOffPercentage, PriceGetter priceGetter, MessageSender messageSender) {
        this.type = type;
        this.longTime = longTime;
        this.shortTime = shortTime;
        this.stockName = stockName;
        this.volume = volume;
        this.strategyID = strategyID;
        this.cutOffPercentage = cutOffPercentage;
        this.priceGetter = priceGetter;
        this.messageSender = messageSender;
    }

    public TwoMovingAverages() {} // Need it for JPA

    public double calculateAverage(int period) throws JSONException {

        JSONArray result = priceGetter.getStockData().get(this.stockName);
        System.out.println("result is:::");
        System.out.println(result);
        double sum = 0;
        if (result != null) {
            for(int i = 0; i < period && i < result.length(); i++) {
                sum += Double.parseDouble(result.getJSONObject(i).getString("price"));
            }
        }

        return sum/period;
    }


    public void performStrategy() throws JSONException {
        this.priceGetter.setStockName(this.stockName);
        this.priceGetter.setNumOfStocks(this.volume);
        double shortAverage = calculateAverage(shortTime);
        double longAverage = calculateAverage(longTime);
        Order o;

        System.out.println("-------------------------------");
        System.out.println("long average: " +  longAverage);
        System.out.println("short average: " + shortAverage);
        System.out.println("-------------------------------");

        if(shortAverage > longAverage) {
            buying = false;
        }else{  //if shortAverage < longAverage
            buying = true;
        }

        if(Math.abs(longAverage - shortAverage) < delta) {
            JSONArray currentStockPrice = this.priceGetter.getStockData().get(stockName);
            if (currentStockPrice != null && currentStockPrice.length() > 0) {
                double currentPrice = Double.parseDouble(currentStockPrice.getJSONObject(0).getString("price"));
                o = new Order(buying, UUID.randomUUID().toString(), currentPrice,
                        volume, stockName, new Date(), "", strategyID);
                addOrder(o);
                System.out.println(o);
                messageSender.sendMessage("queue/OrderBroker", o);
                System.out.println("WE'RE EXECUTING SOMETHING YEAH BOII");
            }
        }
    }
}
