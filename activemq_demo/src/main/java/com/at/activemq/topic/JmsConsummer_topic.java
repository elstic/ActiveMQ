package  com.at.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsummer_topic {
    public static final String ACTIVEMQ_URL = "tcp://192.168.17.3:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws Exception{
        System.out.println(" 这里是 1 号 消费者 ");

        // 1 按照给定的url创建连接工程，这个构造器采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2 通过连接工厂连接 connection  和 启动
        javax.jms.Connection connection = activeMQConnectionFactory.createConnection();
        //  启动
        connection.start();
        // 3 创建回话  session
        // 两个参数，第一个事务， 第二个签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4 创建目的地 （两种 ： 队列/主题   这里用主题）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 5 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener( (message) -> {
            if (null != message  && message instanceof TextMessage){
                     TextMessage textMessage = (TextMessage)message;
                    try {
                      System.out.println("****消费者text的消息："+textMessage.getText());
                    }catch (JMSException e) {
                    }
                }
            if (null != message  && message instanceof MapMessage){
                MapMessage mapMessage = (MapMessage)message;
                try {
                    System.out.println("****消费者的map消息："+mapMessage.getString("k1"));
                }catch (JMSException e) {
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
