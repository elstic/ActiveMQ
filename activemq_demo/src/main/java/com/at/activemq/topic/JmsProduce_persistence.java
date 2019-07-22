package  com.at.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 持久化topic 的消息生产者
public class JmsProduce_persistence {


    public static final String ACTIVEMQ_URL = "tcp://192.168.17.3:61616";
    public static final String TOPIC_NAME = "topic01";


    public static void main(String[] args) throws  Exception{

        // 1 按照给定的url创建连接工程，这个构造器采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2 通过连接工厂连接 connection  和 启动
        javax.jms.Connection connection = activeMQConnectionFactory.createConnection();

        // 3 创建回话  session
        // 两个参数，第一个事务， 第二个签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);
        // 5 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        // 6 通过messageProducer 生产 3 条 消息发送到消息队列中

        // 设置持久化topic 在启动
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //
        connection.start();
        for (int i = 1; i < 4 ; i++) {
            // 7  创建字消息
            TextMessage textMessage = session.createTextMessage("topic_name--" + i);
            // 8  通过messageProducer发布消息
            messageProducer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            //    mapMessage.setString("k1","v1");
            //     messageProducer.send(mapMessage);
        }
        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("  **** TOPIC_NAME消息发送到MQ完成 ****");
    }
}
