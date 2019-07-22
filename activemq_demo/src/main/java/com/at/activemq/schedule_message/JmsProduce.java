package com.at.activemq.schedule_message;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.*;

// 带延时投递的消息生产
public class JmsProduce {

    public static final String ACTIVEMQ_URL = "tcp://192.168.17.3:61616";

    public static final String QUEUE_NAME = "q";


    public static void main(String[] args) throws  Exception{


          ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        //  启动
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer messageProducer = session.createProducer(queue);

        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);   // 持久化  如果开启
        long delay = 3 * 1000 ;
        long perid = 4 * 1000 ;
        int repeat = 7 ;
        for (int i = 1; i < 4 ; i++) {
            TextMessage textMessage = session.createTextMessage("delay msg--" + i);
            // 消息每过 3 秒投递，每 4 秒重复投递一次 ，一共重复投递 7 次
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,perid);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat);

            messageProducer.send(textMessage);
        }
        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
       // session.commit();
        System.out.println("  **** 消息发送到MQ完成 ****");
    }
}
