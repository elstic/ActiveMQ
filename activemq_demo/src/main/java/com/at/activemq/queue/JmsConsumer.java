package  com.at.activemq.queue;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 消息的消费者  也就是回答消息的系统
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://192.168.17.3:61616";
   // public static final String ACTIVEMQ_URL = "nio://192.168.17.3:61608";
    public static final String QUEUE_NAME = "jdbc 01 ";

    public static void main(String[] args) throws Exception{
        System.out.println(" 这里是 3 号 消费者 ");

        // 1 按照给定的url创建连接工程，这个构造器采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2 通过连接工厂连接 connection  和 启动
        javax.jms.Connection connection = activeMQConnectionFactory.createConnection();
        //  启动
        connection.start();
        // 3 创建回话  session
        // 两个参数，第一个事务， 第二个签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4 创建目的地 （两种 ： 队列/主题   这里用队列）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5 创建消息的消费者
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

        // 通过监听的方式来消费消息
        // 通过异步非阻塞的方式消费消息
        // 通过messageConsumer 的setMessageListener 注册一个监听器，
        // 当有消息发送来时，系统自动调用MessageListener 的 onMessage 方法处理消息
        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message)  {
                    if (null != message  && message instanceof TextMessage){
                        TextMessage textMessage = (TextMessage)message;
                        try {
                     System.out.println("****消费者的消息："+textMessage.getText());
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
