package com.citi.dream.rest;

import com.citi.dream.jms.MessageSender;
import com.citi.dream.jms.Order;
import com.citi.dream.rest.requests.StrategyForm;
import com.citi.dream.rest.responses.StrategyResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/strategy")
@CrossOrigin
public class StrategyController {

    @Autowired
    ConfigurableApplicationContext context;

    @Autowired
    MessageSender sender;

    private Logger logger = LogManager.getLogger(this.getClass());

    // Start up a new strategy
    @RequestMapping(method = RequestMethod.POST, value="/start")
    public StrategyResponse startStrategy(@RequestBody StrategyForm strategy) {
        logger.info("Entered startStrategy");
        StrategyResponse resp = new StrategyResponse();
        resp.setResult("successfully started a new strategy of type " + strategy.getType());

        // Generate an ID for the order
        String id = UUID.randomUUID().toString();
        // Send the order to the broker
        Order order = new Order(true, id, 88.0, 200, "HON", new Date(), "");
        sender.sendMessage("queue/OrderBroker", order);

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

    // Listen for the reply of the broker
    /*
    @JmsListener(destination = "queue/OrderBroker_Reply", containerFactory = "orderBrokerReplyContainerFactory")
    public void receiveOrderReply(Order order) {
        logger.info("Broker replied with <" + order + ">");
    }*/
}
