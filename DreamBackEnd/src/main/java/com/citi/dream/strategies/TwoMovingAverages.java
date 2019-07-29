package com.citi.dream.strategies;


import com.citi.dream.jms.MessageSender;
import com.citi.dream.jms.Order;
import org.aspectj.weaver.ast.Or;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.UUID;

public class TwoMovingAverages implements Strategy{

    private String type; //two moving averages
    private int longTime;
    private int shortTime;
    private String stockName;
    private int volume;
    private int strategyID;
    private double cutOffPercentage; //the cutoff that stops the strategy
    private String action; //buy or sell
    boolean buying; //true if buying, false if selling

    @Autowired
    PriceGetter priceGetter = new PriceGetter();

    @Autowired
    MessageSender messageSender = new MessageSender();

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

    public int getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(int strategyID) {
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

    public TwoMovingAverages(String type, int longTime, int shortTime, String stockName, int volume, int strategyID, double cutOffPercentage) {
        this.type = type;
        this.longTime = longTime;
        this.shortTime = shortTime;
        this.stockName = stockName;
        this.volume = volume;
        this.strategyID = strategyID;
        this.cutOffPercentage = cutOffPercentage;
    }

    public double calculateAverage(int period) throws JSONException {
        System.out.println((stockName + period));
        JSONArray result = priceGetter.getStockPriceList(stockName, period);

        double sum = 0;
        double[] priceArray = new double[period];

        for(int i = 0; i < period; i++){
            priceArray[i] = Double.parseDouble(result.getJSONObject(i).getString("price"));
            sum+= Double.parseDouble(result.getJSONObject(i).getString("price"));
        }

        for(int i = 0; i < period; i++){
            System.out.println("AT ELEMENT " +  i + " :" + priceArray[i]);
        }

        return sum/period;
    }

    @Scheduled(fixedRate = 1000)
    public void peformStrategy() throws JSONException {

        double shortAverage = calculateAverage(shortTime);
        double longAverage = calculateAverage(longTime);

        if(shortAverage == longAverage){
            if(buying){
                //send an order here
                Order o = new Order(true, UUID.randomUUID().toString(), priceGetter.getStockPrice(stockName),
                        volume, stockName, new Date(),  "");
                messageSender.sendMessage("queue/OrderBroker",o);
            }
        }
    }



}
