package com.at.activemq.DLQ_TEST;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;


// 测试死信队列
public class JmsConsumer {

    public static final String ACTIVEMQ_URL = "tcp://192.168.17.3:61616";
    public static final String QUEUE_NAME = "queue_cluster";
    public static void main(String[] args) throws Exception{
             ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        RedeliveryPolicy  redeliveryPolicy = new RedeliveryPolicy();
          redeliveryPolicy.setMaximumRedeliveries(3);
        //  当你第四次消费消息的时候就会将消息加入死信队列
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

           Connection connection = activeMQConnectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
           MessageConsumer messageConsumer = session.createConsumer(queue);

        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message)  {
                try {
                    if (null != message  && message instanceof TextMessage){
                        TextMessage textMessage = (TextMessage)message;
                     System.out.println("****cluster msg："+textMessage.getText());
                }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}