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

    // Create strategy
    public Strategy createStrategy(String type, ArrayList<Integer> timeList,
                                   String stockName, int volume, String strategyID, double cutOffPercentage) {
        if (type.equals("two moving averages")) {
            logger.info("Creating a two moving averages strategy");
            logger.info("longTime: "+ timeList.get(0) + " shortTime: " + timeList.get(1));
            TwoMovingAverages newStrategy = new TwoMovingAverages(type, timeList.get(0),
                    timeList.get(1), stockName, volume, strategyID, cutOffPercentage, priceGetter, messageSender);
            twoMovingAveragesRepository.save(newStrategy);
            strategies.put(strategyID, newStrategy);
            return newStrategy;
        } else if (type.equals("bollinger band")) {
            logger.info("Creating a bollinger band strategy");
            logger.info("durationTime: "+ timeList.get(2);

            BollingerBand newStrategy = new BollingerBand(type, timeList.get(2), stockName, volume,
                    strategyID, cutOffPercentage, priceGetter, messageSender);
            bollingerBandRepository.save(newStrategy);
            strategies.put(strategyID, newStrategy);
            return newStrategy;
        }


        return null;
    }

    // Pause strategy
    public boolean pauseStrategy(String strategyID) {
        // Set stategy's state to be paused
        Strategy currentStrategy = strategies.get(strategyID);
        if (currentStrategy != null && currentStrategy.getState().equals("running")) {
            logger.info("pausing strategy <" + strategyID + ">");
            currentStrategy.setState("paused");
            return true;
        } else {
            logger.warn("pausing a non-existent/blocked strategy");
            return false;
        }
    }

    // Resume strategy
    public boolean resumeStrategy(String strategyID) {
        Strategy currentStrategy = strategies.get(strategyID);
        if (currentStrategy != null && currentStrategy.getState().equals("paused")) {
            logger.info("resuming strategy <" + strategyID + ">");
            currentStrategy.setState("running");
            return true;
        } else {
            logger.warn("resuming a non-existent/stopped strategy");
            return false;
        }

    }

    // Stopping strategy
    public boolean stopStrategy(String strategyID) {
        // Remove it from the running strategies and update its state to stopped
        Strategy currentStrategy = twoMovingAveragesRepository.findById(strategyID).orElse(null);
        if (currentStrategy != null) {
            logger.info("stopping strategy <" + strategyID + ">");
            currentStrategy.setState("stopped");
            if (currentStrategy.getType().equals("two moving averages")) {
                twoMovingAveragesRepository.save((TwoMovingAverages) currentStrategy);
            }
            // TODO after bollinger is finished add condition
            strategies.remove(strategyID);
            return true;
        } else {
            logger.warn("stopping a non-existent strategy");
            return false;
        }
    }

    // Save the strategies every so often
    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    public void updateStrategies() {
        logger.info("updating all the strategies in to the DB");
        for (String strategyID : strategies.keySet()) {
            Strategy currentStrategy = strategies.get(strategyID);
            if (currentStrategy.getType().equals("two moving averages")) {
                twoMovingAveragesRepository.save((TwoMovingAverages) currentStrategy);
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void runStrategies() throws JSONException {
        logger.info("Running strategies");
        for (String strategyID : strategies.keySet()) {
            Strategy currentStrategy = strategies.get(strategyID);
            if (currentStrategy.getState().equals("running")) {
                currentStrategy.performStrategy();
            }
        }
    }
}
