package com.at.boot.activemq.topic;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MainApp_TopicProduce {

    public static void main(String[] args) {
        SpringApplication.run(MainApp_TopicProduce.class,args);
    }
}
