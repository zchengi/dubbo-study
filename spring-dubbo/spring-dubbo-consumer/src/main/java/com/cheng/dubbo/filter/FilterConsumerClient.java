package com.cheng.dubbo.filter;

import com.cheng.dubbo.ServiceAPI;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * @author cheng
 *         2019/1/18 22:01
 */
public class FilterConsumerClient {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dubbo-consumer.xml");

        context.start();

        while (true) {

            Scanner sc = new Scanner(System.in);
            String message = sc.next();

            // 获取接口
            ServiceAPI serviceApi = (ServiceAPI) context.getBean("consumerService");

            // 测试 负载均衡
            for (int i = 0; i < 2; i++) {
                System.out.println(serviceApi.sendMessage(message + "-" + i));
            }
        }
    }
}
