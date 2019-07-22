package com.at.activemq.colony;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer_colony {
    // failover 带故障迁移的多节点集群配置
    public static final String ACTIVEMQ_URL = "failover:(tcp://192.168.17.3:61616,tcp://192.168.17.3:61617,tcp://192.168.17.3:61618)?randomize=false";

    public static final String QUEUE_NAME = "queue_cluster";
    public static void main(String[] args) throws Exception{
            ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
           Connection connection = activeMQConnectionFactory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
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