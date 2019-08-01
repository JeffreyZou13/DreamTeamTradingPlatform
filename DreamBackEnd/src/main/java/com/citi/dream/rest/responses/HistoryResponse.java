package com.citi.dream.rest.responses;

import com.citi.dream.jms.Order;
import com.citi.dream.strategies.Strategy;

import java.util.HashMap;
import java.util.List;

public class HistoryResponse {
    private String result;
    private HashMap<String, List<? extends Strategy>> strategies;
    private Strategy strategy;
    private List<Order> orders;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HashMap<String, List<? extends Strategy>> getStrategies() {
        return strategies;
    }

    public void setStrategies(HashMap<String, List<? extends Strategy>> strategies) {
        this.strategies = strategies;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
