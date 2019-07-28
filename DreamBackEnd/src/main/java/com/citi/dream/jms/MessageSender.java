package com.citi.dream.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class MessageSender {

    @Autowired
    ConfigurableApplicationContext context;

    private Logger logger = LogManager.getLogger(this.getClass());

    // Send a message to specific location in the form of Order
    public void sendMessage(String destinationName, Order order) {
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        logger.info("Sending a message <" + order + ">");
        jmsTemplate.send(destinationName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(order);
            }
        });
    }
}
