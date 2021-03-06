package com.citi.dream.jms;


import com.citi.dream.strategies.BollingerBand;

import com.citi.dream.strategies.TwoMovingAverages;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="orders")
public class Order implements Serializable {

    @Id
    @Column(name="orderID")
    private String id;

    @Column(name="buy") private boolean buy;
    @Column(name="price") private double price;
    @Column(name="size") private int size;
    @Column(name="stock") private String stock;
    @Column(name="when_as_date") private Date whenAsDate;
    @Column(name="response") private String response;
    @Column(name="strategyID") private String strategyID;
    @Column(name="strategy_type") private String strategyType;
    @Column(name="profit") private double profit;
    @Column(name="pnl") private double pnl;

    public double getPnl() {
        return pnl;
    }

    public void setPnl(double pnl) {
        this.pnl = pnl;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }


//    ================================
    public TwoMovingAverages getTwoMovingAverages() {
        return twoMovingAverages;
    }

    public void setTwoMovingAverages(TwoMovingAverages twoMovingAverages) {
        this.twoMovingAverages = twoMovingAverages;
    }

    @JoinColumn (name="two_avg_id", referencedColumnName ="strategyID", nullable = true)
    @ManyToOne
    @com.fasterxml.jackson.annotation.JsonIgnore
    private TwoMovingAverages twoMovingAverages;
//================================

    public BollingerBand getBollingerBand() {
        return bollingerBand;
    }

    public void setBollingerBand(BollingerBand bollingerBand) {
        this.bollingerBand = bollingerBand;
    }

    @JoinColumn (name="bollinger_band_id", referencedColumnName ="strategyID", nullable = true)
    @ManyToOne
    @com.fasterxml.jackson.annotation.JsonIgnore
    private BollingerBand bollingerBand;

    public Order(boolean buy, String id, double price, int size, String stock, Date whenAsDate,
                 String response, String strategyID, String strategyType, double profit,
                 double pnl) {
        this.id = id;
        this.buy = buy;
        this.price = price;
        this.size = size;
        this.stock = stock;
        this.whenAsDate = whenAsDate;
        this.response = response;
        this.strategyID = strategyID;
        this.strategyType = strategyType;
        this.profit = profit;
        this.pnl = pnl;
    }

    public Order() {} // Need this for JPA

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Date getWhenAsDate() {
        return whenAsDate;
    }

    public void setWhenAsDate(Date whenAsDate) {
        this.whenAsDate = whenAsDate;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(String strategyID) {
        this.strategyID = strategyID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "buy=" + buy +
                ", id=" + id +
                ", price=" + price +
                ", size=" + size +
                ", stock='" + stock + '\'' +
                ", whenAsDate=" + whenAsDate +
                ", response='" + response + '\'' +
                ", strategyID='" + strategyID + '\'' +
                ", strategyType='" + strategyType + '\'' +
                ", profit='" + profit + '\'' +
                '}';
    }
}
