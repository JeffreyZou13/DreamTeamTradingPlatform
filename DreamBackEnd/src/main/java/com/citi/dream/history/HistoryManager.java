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
        logger.info("Getting all strategies");
        return twoMovingAveragesRepository.findAll();
    }

    public TwoMovingAverages getStrategy(String id) {
        logger.info("Getting stategy with id <" + id + ">");
        return twoMovingAveragesRepository.findById(id).orElse(null);
    }

    public List<Order> getOrders() {
        logger.info("Getting all orders");
        return orderRepository.findAll();
    }

    public Order getOrder(String id) {
        logger.info("Getting order with id <" + id + ">");
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getOrdersByStrategyID(String id) {
        logger.info("Orders by strategy ID, sorted by time");
        return orderRepository.findByStrategyIDOrderByWhenAsDate(id);
    }

    public List<Order> getOrdersPnlByStrategyType(String type) {
        logger.info("Getting orders by strategy type <" + type + ">");
        return orderRepository.findByStrategyTypeOrderByWhenAsDate(type);
    }
}
