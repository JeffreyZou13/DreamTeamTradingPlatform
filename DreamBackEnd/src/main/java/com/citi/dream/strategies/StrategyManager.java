package com.citi.dream.strategies;

import com.citi.dream.jms.MessageSender;
import com.citi.dream.repos.BollingerBandRepository;
import com.citi.dream.repos.TwoMovingAveragesRepository;
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
    @Autowired
    TwoMovingAveragesRepository twoMovingAveragesRepository;
    BollingerBandRepository bollingerBandRepository;

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
            TwoMovingAverages newStrategy = new TwoMovingAverages(type, longTime, shortTime, stockName, volume, strategyID, cutOffPercentage, priceGetter, messageSender);
            twoMovingAveragesRepository.save(newStrategy);
            strategies.put(strategyID, newStrategy);
            return newStrategy;
        } else if (type.equals("bollinger band")) {
            logger.info("Creating a bollinger band strategy");
            BollingerBand newStrategy = new BollingerBand(type, longTime, stockName, volume,
                    strategyID, cutOffPercentage, priceGetter, messageSender);
            bollingerBandRepository.save(newStrategy);
            strategies.put(strategyID, newStrategy);
            return newStrategy;
        }


        return null;
    }

    // Stopping strategy
    public Strategy stopStrategy(String strategyID) {
        // Remove it from the running strategies and update it to active
        return strategies.remove(strategyID);
    }

    public Strategy deleteStrategy(String strategyID) {
        twoMovingAveragesRepository.deleteById(strategyID);
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
