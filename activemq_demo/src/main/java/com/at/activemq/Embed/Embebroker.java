package  com.at.activemq.Embed;

import org.apache.activemq.broker.BrokerService;

public class Embebroker {
    public static void main(String[] args) throws Exception {
        // broker 服务
        BrokerService brokerService = new BrokerService();
        // 把小型 activemq 服务器嵌入到 java 代码
        brokerService.setUseJmx(true);
        // 原本的是 192.……  是linux 上的服务器，而这里是本地windows 的小型mq 服务器
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
