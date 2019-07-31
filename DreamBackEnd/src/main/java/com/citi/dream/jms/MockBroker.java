package com.citi.dream.jms;

import com.citi.dream.repos.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class MockBroker {

    @Autowired
    ConfigurableApplicationContext context;

    @Autowired
    MessageSender sender;

    @Autowired
    OrderRepository orderRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @JmsListener(destination = "queue/OrderBroker", containerFactory = "orderBrokerContainerFactory")
    public void receiveOrder(Order order) {
        logger.info("Received order <" + order + ">");
        order.setResponse("FILLED");
        sender.sendMessage("queue/OrderBroker_Reply", order);
        orderRepository.save(order);
    }
}
