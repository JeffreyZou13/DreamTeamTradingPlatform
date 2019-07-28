package com.citi.dream.jms;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private boolean buy;
    private String id;
    private double price;
    private int size;
    private String stock;
    private Date whenAsDate;
    private String response;


    public Order(boolean buy, String id, double price, int size, String stock, Date whenAsDate, String response) {
        this.buy = buy;
        this.id = id;
        this.price = price;
        this.size = size;
        this.stock = stock;
        this.whenAsDate = whenAsDate;
        this.response = response;
    }

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
                '}';
    }
}
