package com.citi.dream.strategies;

public class TwoMovingAverages implements Strategy{

    private String type;
    private int longTime;
    private int shortTime;
    private String stockName;
    private int volume;
    private int strategyID;
    private double cutOffPercentage; //the cutoff that stops the strategy


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


    public TwoMovingAverages(String type, int longTime, int shortTime, String stockName, int volume, int strategyID, double cutOffPercentage) {
        this.type = type;
        this.longTime = longTime;
        this.shortTime = shortTime;
        this.stockName = stockName;
        this.volume = volume;
        this.strategyID = strategyID;
        this.cutOffPercentage = cutOffPercentage;


    }


}
