package com.cheng.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author cheng
 *         2018/12/31 23:27
 */
public class ProviderConnect {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dubbo-provider-connect.xml");

        context.start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
