package com.at.activemq.schedule_message;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// // 带延时投递的消息消费
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://192.168.17.3:61616";
     public static final String QUEUE_NAME = "jdbc 01 ";

    public static void main(String[] args) throws Exception{
          ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
         Connection connection = activeMQConnectionFactory.createConnection();
          connection.start();
          Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
          Queue queue = session.createQueue(QUEUE_NAME);
         MessageConsumer messageConsumer = session.createConsumer(queue);

      /*
          同步阻塞方式reveive()   空参数的receive方法是阻塞，有参数的为等待时间
          订阅者或消费者使用MessageConsumer 的receive() 方法接收消息，receive 在接收之前一直阻塞
            while(true){
            // 这里是 TextMessage 是因为消息发送者是 TextMessage ， 接受处理的
            // 也应该是这个类型的消息
            TextMessage message = (TextMessage)messageConsumer.receive(4000L);  // 4秒
            if (null != message){
                System.out.println("****消费者的消息："+message.getText());
            }else {
                break;
            }
        }
       */


        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message)  {
                    if (null != message  && message instanceof TextMessage){
                        TextMessage textMessage = (TextMessage)message;
                        try {
                     System.out.println("延时 "+textMessage.getText());
                    }catch (JMSException e) {
                            e.printStackTrace();
                        }
                }
            }
        });

        // 保证控制台不灭  不然activemq 还没连上就关掉了连接
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
