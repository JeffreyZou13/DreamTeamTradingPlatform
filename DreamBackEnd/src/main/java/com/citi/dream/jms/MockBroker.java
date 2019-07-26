package com.citi.dream.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MockBroker {

    private Logger logger = LogManager.getLogger(this.getClass());

    @JmsListener(destination = "queue/OrderBroker", containerFactory = "orderBrokerContainerFactory")
    public void receiveMessage(String message) {
        logger.info("Received message <" + message + ">");
    }
}
