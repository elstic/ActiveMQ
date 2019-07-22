package com.at.boot.activemq.consummer;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Component
public class Queue_consummer {

    @JmsListener(destination = "${myqueue}")     // 注解监听
    public void receive(TextMessage textMessage) throws  Exception{
        System.out.println(" ***  消费者收到消息  ***"+textMessage.getText());
    }

}
