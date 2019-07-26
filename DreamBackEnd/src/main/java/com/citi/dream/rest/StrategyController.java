package com.citi.dream.rest;

import com.citi.dream.entities.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin
public class StrategyController {

    private Logger logger = LogManager.getLogger(this.getClass());

    // Start up a new strategy
    @RequestMapping(method = RequestMethod.POST, value="/strategy/start")
    public String startStrategy(@RequestBody Strategy strategy) {
        logger.info("Entered startStrategy");
        return "successfully started a new strategy of type " + strategy.getType();
    }

    // Pause a strategy
    @RequestMapping(method = RequestMethod.POST, value="/strategy/pause")
    public String pauseStrategy(@RequestBody Strategy strategy) {
        logger.info("Entered pauseStrategy");
        return "successfully paused strategy of type " + strategy.getType();
    }

    // Stop a strategy
    @RequestMapping(method = RequestMethod.POST, value="/strategy/stop")
    public String stopStrategy(@RequestBody Strategy strategy) {
        logger.info("Entered stopStrategy");
        return "successfully stopped strategy of type " + strategy.getType();
    }
}
