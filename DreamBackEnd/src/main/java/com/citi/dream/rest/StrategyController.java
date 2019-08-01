package com.citi.dream.rest;

import com.citi.dream.rest.requests.StrategyForm;
import com.citi.dream.rest.responses.StrategyResponse;
import com.citi.dream.strategies.Strategy;
import com.citi.dream.strategies.StrategyManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/strategy")
@CrossOrigin(origins="http://localhost:4200")
public class StrategyController {

    @Autowired
    StrategyManager strategyManager;

    private Logger logger = LogManager.getLogger(this.getClass());

    // Start up a new strategy
    @RequestMapping(method = RequestMethod.POST, value="/start", consumes="application/json")
    public StrategyResponse startStrategy(@RequestBody StrategyForm strategy) {



        ArrayList<Integer> timeList = new ArrayList<>();
//        timeList.add(strategy.getLongPeriod());
//        timeList.add(strategy.getShortPeriod());
//        timeList.add(strategy.getDurationTime());



        //        for testing bollinger band only,
//        please bypass it later after changing frontend input
        strategy.setType("bollinger band");
        timeList.add(strategy.getLongPeriod());
        timeList.add(strategy.getShortPeriod());
        timeList.add(10);
//        ===============
        logger.info("Entered startStrategy");
        logger.info(strategy.getType());
        logger.info(strategy.getStock());
        StrategyResponse resp = new StrategyResponse();


        // Generate an ID and create a new strategy
        String strategyId = UUID.randomUUID().toString();



        strategyManager.createStrategy(strategy.getType(), timeList,
                strategy.getStock(), strategy.getSize(), strategyId, 0.001);
        resp.setResult("successfully started a new strategy of type " + strategy.getType() + " with id " + strategyId);
        resp.setId(strategyId);
        return resp;
    }

    // Pause a strategy
    @RequestMapping(method = RequestMethod.POST, value="/pause")
    public StrategyResponse pauseStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered pauseStrategy");
        StrategyResponse resp = new StrategyResponse();
        boolean paused = strategyManager.pauseStrategy(strategy.getId());
        if (paused) {
            resp.setResult("successfully paused strategy <" + strategy.getId() + ">");
        } else {
            resp.setResult("failure: strategy not found");
        }
        resp.setId(strategy.getId());
        return resp;
    }

    // Resume a strategy
    @RequestMapping(method = RequestMethod.POST, value="/resume")
    public StrategyResponse resumeStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered resumeStrategy");
        StrategyResponse resp = new StrategyResponse();
        boolean resumed = strategyManager.resumeStrategy(strategy.getId());
        if (resumed) {
            resp.setResult("successfully resumed stategy <" + strategy.getId() + ">");
        } else {
            resp.setResult("failure: strategy not found or is stopped");
        }
        resp.setId(strategy.getId());
        return resp;
    }

    // Stop a strategy
    @RequestMapping(method = RequestMethod.POST, value="/stop")
    public StrategyResponse stopStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered stopStrategy");
        StrategyResponse resp = new StrategyResponse();
        boolean stopped = strategyManager.stopStrategy(strategy.getId());
        if (stopped) {
            resp.setResult("successfully stopped strategy <" + strategy.getId() + ">");
        } else {
            resp.setResult("failure: strategy not found");
        }
        resp.setId(strategy.getId());
        return resp;
    }
}
