package com.citi.dream.services;

import com.citi.dream.entities.Strategy;

public interface StrategyService {
    Strategy addStrategy(Strategy strategy);
    void deleteStrategy(Strategy strategy);
}
