package com.cheng.dubbo.consumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.cheng.dubbo.consumer.quickstart.QuickStartConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDubboConfiguration
@SpringBootApplication
public class SpringbootConsumerApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(SpringbootConsumerApplication.class, args);

        QuickStartConsumer quickStartConsumer = (QuickStartConsumer) run.getBean("quickStartConsumer");
        quickStartConsumer.sendMessage("SpringBoot + Dubbo + Zookeeper");
    }

}

