package com.citi.dream.strategies;

import com.citi.dream.jms.MessageSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class StrategyManager {
    @Autowired
    PriceGetter priceGetter;
    @Autowired
    MessageSender messageSender;

    private Logger logger = LogManager.getLogger(this.getClass());
    private HashMap<String, Strategy> strategies = new HashMap<>();

    public HashMap<String, Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(HashMap<String, Strategy> strategies) {
        this.strategies = strategies;
    }

    public Strategy createStrategy(String type, int longTime, int shortTime, String stockName, int volume, String strategyID, double cutOffPercentage) {
        if (type.equals("two moving averages")) {
            logger.info("Creating a two moving averages strategy");
            Strategy newStrategy = new TwoMovingAverages(type, longTime, shortTime, stockName, volume, strategyID, cutOffPercentage, priceGetter, messageSender);
            strategies.put(strategyID, newStrategy);
            return newStrategy;
        }
        return null;
    }

    public Strategy deleteStrategy(String strategyID) {
        return strategies.remove(strategyID);
    }

    // TODO schedule strategy
    @Scheduled(fixedDelay = 1000)
    public void runStrategies() throws JSONException {
        logger.info("Running strategies");
        for (String strategyID : strategies.keySet()) {
            // TODO Check if paused
            strategies.get(strategyID).performStrategy();
        }
    }
}
