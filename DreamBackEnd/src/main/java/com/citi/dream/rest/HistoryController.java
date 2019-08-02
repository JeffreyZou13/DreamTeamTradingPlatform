package com.citi.dream.rest;

import com.citi.dream.history.HistoryManager;
import com.citi.dream.rest.responses.HistoryResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:4200")
public class HistoryController {

    @Autowired
    HistoryManager historyManager;

    private Logger logger = LogManager.getLogger(this.getClass());

    // All strategies
    @RequestMapping(method = RequestMethod.GET, value = "/strategies")
    public HistoryResponse getStrategies() {
        logger.info("Getting all strategies");
        HistoryResponse resp = new HistoryResponse();
        resp.setStrategies(historyManager.getStrategies());
        resp.setResult("successfully got all strategies");
        return resp;
    }

    // All running/paused strategies
    @RequestMapping(method = RequestMethod.GET, value = "/strategies/notstopped")
    public HistoryResponse getNotStoppedStrategies() {
        logger.info("Getting all not stopped strategies");
        HistoryResponse resp = new HistoryResponse();
        resp.setStrategies(historyManager.getNotStoppedStrategies());
        resp.setResult("successfully got all not stopped strategies");
        return resp;
    }

    // Strategy by id
    @RequestMapping(method = RequestMethod.GET, value = "/strategy/{id}")
    public HistoryResponse getStrategy(@PathVariable String id) {
        logger.info("Getting strategy " + id);
        HistoryResponse resp = new HistoryResponse();
        resp.setStrategy(historyManager.getStrategy(id));
        resp.setResult("successfully got strategy with id <" + id + ">");
        return resp;
    }

    // All orders
    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public HistoryResponse getOrders() {
        logger.info("Getting all orders");
        HistoryResponse resp = new HistoryResponse();
        resp.setOrders(historyManager.getOrders());
        resp.setResult("successfully got all orders");
        return resp;
    }

    // All orders placed by a strategy
    @RequestMapping(method = RequestMethod.GET, value = "/orders/{id}")
    public HistoryResponse getOrdersByStrategyID(@PathVariable String id) {
        logger.info("Getting all orders under strategy " + id);
        HistoryResponse resp = new HistoryResponse();
        resp.setOrders(historyManager.getOrdersByStrategyID(id));
        resp.setResult("successfully got all orders under strategy <" + id + ">");
        return resp;
    }

    // View of orders by strategy type, sorted by time
    @RequestMapping(method = RequestMethod.GET, value = "/orders/pnl/{type}")
    public HistoryResponse getOrdersPnlByStrategyType(@PathVariable String type) {
        logger.info("Getting orders by strategy type, sorted by time");
        HistoryResponse resp = new HistoryResponse();
        resp.setOrders(historyManager.getOrdersPnlByStrategyType(type));
        resp.setResult("successfully got orders by strategy <" + type + "> sorted by time ascending");
        return resp;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders/pnlpercentage/{strategy_id}")
    public HistoryResponse getOrdersPnlPercentageByStrategyID(@PathVariable String strategy_id) {
        logger.info("Getting orders by strategy id, sorted by time");
        HistoryResponse resp = new HistoryResponse();
        resp.setOrders(historyManager.getOrdersPnlPercentageByStrategyID(strategy_id));
        resp.setResult("successfully got orders by strategy id <" + strategy_id + "> sorted by time " +
                "ascending");
        return resp;
    }
}
