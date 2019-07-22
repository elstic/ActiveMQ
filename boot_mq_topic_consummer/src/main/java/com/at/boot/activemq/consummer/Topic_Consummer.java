package com.at.boot.activemq.consummer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class Topic_Consummer {

    @JmsListener(destination = "${mytopic}")
    public void receive(TextMessage textMessage) throws  Exception{
        System.out.println("消费者受到订阅的主题："+textMessage.getText());
    }

}
