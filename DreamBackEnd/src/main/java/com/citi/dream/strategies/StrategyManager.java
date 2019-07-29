package com.citi.dream.strategies;

import com.citi.dream.jms.MessageSender;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StrategyManager {
    @Autowired
    PriceGetter priceGetter;
    @Autowired
    MessageSender messageSender;
    private ArrayList<Strategy> strategies = new ArrayList<>();

    public ArrayList<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(ArrayList<Strategy> strategies) {
        this.strategies = strategies;
    }

    public Strategy createStrategy(String type, int longTime, int shortTime, String stockName, int volume, String strategyID, double cutOffPercentage) {
        if (type.equals("two moving averages")) {
            Strategy newStrategy = new TwoMovingAverages(type, longTime, shortTime, stockName, volume, strategyID, cutOffPercentage, priceGetter, messageSender);
            strategies.add(newStrategy);
            return newStrategy;
        }
        return null;
    }

    // TODO chedule strategy
    @Scheduled(fixedDelay = 1000)
    public void runStrategies() throws JSONException {
        for (Strategy strategy : strategies) {
            strategy.performStrategy();
        }
    }
}
