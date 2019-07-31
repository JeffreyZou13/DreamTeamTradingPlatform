package com.citi.dream.history;

import com.citi.dream.jms.Order;
import com.citi.dream.repos.OrderRepository;
import com.citi.dream.repos.TwoMovingAveragesRepository;
import com.citi.dream.strategies.Strategy;
import com.citi.dream.strategies.TwoMovingAverages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryManager {
    @Autowired
    TwoMovingAveragesRepository twoMovingAveragesRepository;
    @Autowired
    OrderRepository orderRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    public List<? extends Strategy> getStrategies() {
        return twoMovingAveragesRepository.findAll();
    }

    public TwoMovingAverages getStrategy(String id) {
        return twoMovingAveragesRepository.findById(id).orElse(null);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getOrdersByStrategyID(String id) {
        return orderRepository.findByStrategyID(id);
    }

    public List<Order> getOrdersPnlByStrategyType(String type) {
        return null;
    }
}
