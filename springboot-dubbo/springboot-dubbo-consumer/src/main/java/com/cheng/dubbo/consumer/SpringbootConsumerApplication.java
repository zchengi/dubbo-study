package com.cheng.dubbo.consumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.cheng.dubbo.consumer.transaction.TransactionConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDubboConfiguration
@SpringBootApplication
public class SpringbootConsumerApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(SpringbootConsumerApplication.class, args);

//        QuickStartConsumer quickStartConsumer = (QuickStartConsumer) run.getBean("quickStartConsumer");
//        quickStartConsumer.sendMessage("SpringBoot + Dubbo + Zookeeper");

        // 测试分布式事务的使用
        TransactionConsumer transactionConsumer = (TransactionConsumer) run.getBean("transactionConsumer");


        // 1,2,3    4,5
//        transactionConsumer.sendMessage("1,2,3");
//        transactionConsumer.sendMessage("4,5");
        transactionConsumer.sendMessage("7");
    }

}

