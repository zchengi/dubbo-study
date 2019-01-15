package com.cheng.dubbo.group;

import com.cheng.dubbo.ServiceAPI;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Scanner;

/**
 * @author cheng
 *         2018/12/31 23:39
 */
public class GroupConsumerClient {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dubbo-consumer-group.xml");

        context.start();

        while (true) {

            Scanner sc = new Scanner(System.in);
            String message = sc.next();

            // 获取接口
            ServiceAPI serviceApi = (ServiceAPI) context.getBean("consumerService");

            List<String> strings = serviceApi.mergeTest(message);
            strings.forEach(System.out::println);
        }
    }
}
