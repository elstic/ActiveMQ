package com.at.activemq.spring;


import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

@Service
public class SpringMQ_producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext  ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_producer producer = (SpringMQ_producer) ctx.getBean("springMQ_Producer");

        producer.jmsTemplate.send((session) -> {
            TextMessage textMessage = session.createTextMessage("spring 和 activemq 的整合");
            return textMessage;
        });
        System.out.println(" *** send task over ***");
    }
}
