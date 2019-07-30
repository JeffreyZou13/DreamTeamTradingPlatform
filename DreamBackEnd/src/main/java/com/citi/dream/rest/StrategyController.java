package com.citi.dream.rest;

import com.citi.dream.rest.requests.StrategyForm;
import com.citi.dream.rest.responses.StrategyResponse;
import com.citi.dream.strategies.StrategyManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/strategy")
@CrossOrigin
public class StrategyController {

    @Autowired
    ConfigurableApplicationContext context;

    @Autowired
    StrategyManager strategyManager;

    private Logger logger = LogManager.getLogger(this.getClass());

    // Start up a new strategy
    @RequestMapping(method = RequestMethod.POST, value="/start")
    public StrategyResponse startStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered startStrategy");
        StrategyResponse resp = new StrategyResponse();

        // Generate an ID and create a new strategy
        String strategyId = UUID.randomUUID().toString();
        strategyManager.createStrategy(strategy.getType(), strategy.getLongPeriod(), strategy.getShortPeriod(),
                strategy.getStock(), strategy.getSize(), strategyId, 0.01);
        resp.setResult("successfully started a new strategy of type " + strategy.getType());
        resp.setId(strategyId);
        return resp;
    }

    // Pause a strategy
    @RequestMapping(method = RequestMethod.POST, value="/pause")
    public StrategyResponse pauseStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered pauseStrategy");
        StrategyResponse resp = new StrategyResponse();
        resp.setResult("successfully paused strategy of type " + strategy.getType());
        return resp;
    }

    // Stop a strategy
    @RequestMapping(method = RequestMethod.POST, value="/stop")
    public StrategyResponse stopStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered stopStrategy");
        StrategyResponse resp = new StrategyResponse();
        resp.setResult("successfully stopped strategy of type " + strategy.getType());
        return resp;
    }
}
