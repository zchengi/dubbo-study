package com.cheng.dubbo.connect;

import com.cheng.dubbo.ServiceAPI;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * @author cheng
 *         2018/12/31 23:39
 */
public class ConsumerConnect1 {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dubbo-consumer.xml");

        context.start();

        while (true) {

            Scanner sc = new Scanner(System.in);
            String message = sc.next();

            // 获取接口
            ServiceAPI serviceApi = (ServiceAPI) context.getBean("consumerService");

            // 测试 负载均衡
            for (int i = 0; i < 10; i++) {
                System.out.println(serviceApi.sendMessage(message+"-" + i));
            }

        }
    }
}
