package com.citi.dream.history;

import com.citi.dream.jms.Order;
import com.citi.dream.repos.BollingerBandRepository;
import com.citi.dream.repos.OrderRepository;
import com.citi.dream.repos.TwoMovingAveragesRepository;
import com.citi.dream.strategies.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class HistoryManager {
    @Autowired
    TwoMovingAveragesRepository twoMovingAveragesRepository;
    @Autowired
    BollingerBandRepository bollingerBandRepository;
    @Autowired
    OrderRepository orderRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    public HashMap<String, List<? extends Strategy>> getStrategies() {
        logger.info("Getting all strategies");
        HashMap<String, List<? extends Strategy>> strategies = new HashMap<>();
        strategies.put("two moving averages", twoMovingAveragesRepository.findAll());
        strategies.put("bollinger band", bollingerBandRepository.findAll());
        return strategies;
    }

    public HashMap<String, List<? extends Strategy>> getNotStoppedStrategies() {
        logger.info("Getting all not stopped strategies");
        HashMap<String, List<? extends Strategy>> strategies = new HashMap<>();
        strategies.put("two moving averages", twoMovingAveragesRepository.findAllNotStopped());
        strategies.put("bollinger band", bollingerBandRepository.findAllNotStopped());
        return strategies;
    }

    public Strategy getStrategy(String id) {
        logger.info("Getting stategy with id <" + id + ">");
        Strategy strategy = twoMovingAveragesRepository.findById(id).orElse(null);
        if (strategy == null) {
            strategy = bollingerBandRepository.findById(id).orElse(null);
        }
        return strategy;
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

    public List<Order> getOrdersPnlPercentageByStrategyID(String stratedy_id) {
        logger.info("Getting orders by strategy id <" + stratedy_id + ">");
        return orderRepository.findByStrategyIDOrderByWhenAsDate(stratedy_id);
    }
}
