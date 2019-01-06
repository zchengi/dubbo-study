package com.cheng.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * @author cheng
 *         2018/12/31 23:39
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dubbo-consumer.xml");

        context.start();

        while (true) {

            Scanner sc = new Scanner(System.in);
            String message = sc.next();

            // 获取接口
            ServiceAPI serviceApi = (ServiceAPI) context.getBean("consumerService");

            for (int i = 0; i < 10; i++) {
                System.out.println(serviceApi.sendMessage(message+"-" + i));
            }
        }
    }
}
