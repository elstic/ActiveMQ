package com.at.activemq.call_back;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;


// 带接收回调的异步发送
public class JmsProduce {
            public static final String ACTIVEMQ_URL = "tcp://192.168.17.3:61616";
       public static final String QUEUE_NAME = "jdbc01";


    public static void main(String[] args) throws  Exception{

          ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 设置允许有数据丢失  
       activeMQConnectionFactory.setUseAsyncSend(true);

            Connection connection = activeMQConnectionFactory.createConnection();
          connection.start();
           Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
          Queue queue = session.createQueue(QUEUE_NAME);
         ActiveMQMessageProducer messageProducer = (ActiveMQMessageProducer)session.createProducer(queue);
          messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);   // 持久化  如果开启
              TextMessage textMessage = null ;
                for (int i = 1; i < 4 ; i++) {
                 textMessage = session.createTextMessage("msg--" + i);
              textMessage.setJMSMessageID(UUID.randomUUID().toString()+"--  orderr");
             String msgid = textMessage.getJMSMessageID();
                    messageProducer.send(textMessage, new AsyncCallback() {
                        @Override
                        public void onSuccess() {
                            // 发送成功怎么样
                            System.out.println(msgid+"has been successful send ");
                        }

                        @Override
                        public void onException(JMSException e) {
                            // 发送失败怎么样
                            System.out.println(msgid+" has been failure send ");
                        }
                    });
        }
          messageProducer.close();
        session.close();
        connection.close();
       // session.commit();
        System.out.println("  **** 消息发送到MQ完成 ****");
    }
}
