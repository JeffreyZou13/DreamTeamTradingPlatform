package com.citi.dream.rest;

import com.citi.dream.rest.requests.StrategyForm;
import com.citi.dream.rest.responses.StrategyResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/strategy")
@CrossOrigin
public class StrategyController {

    private Logger logger = LogManager.getLogger(this.getClass());

    // Start up a new strategy
    @RequestMapping(method = RequestMethod.POST, value="/start")
    public StrategyResponse startStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered startStrategy");
        StrategyResponse resp = new StrategyResponse();
        resp.setResult("successfully started a new strategy of type " + strategy.getType());
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
