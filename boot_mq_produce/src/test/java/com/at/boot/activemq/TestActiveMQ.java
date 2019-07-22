package com.at.boot.activemq;

import com.at.boot.activemq.produce.Queue_Produce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@SpringBootTest(classes = MainApp_Produce.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestActiveMQ {

    @Resource    //  这个是java 的注解，而Autowried  是 spring 的
    private Queue_Produce  queue_produce ;

    @Test
    public  void testSend() throws Exception{
        queue_produce.produceMessage();
    }
}
