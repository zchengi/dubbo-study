package com.cheng.dubbo.provider;

import com.cheng.dubbo.ServiceAPI;

import java.util.List;

/**
 * @author cheng
 *         2018/12/31 23:11
 */
public class QuickStartService implements ServiceAPI {

    @Override
    public String sendMessage(String message)  {

        System.out.println("sendMessage: " + message);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "quick-start-provider-message: " + message;
    }

    @Override
    public String sendMessage2(String message)  {

        System.out.println("sendMessage2: " + message);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "quick-start-provider-message2: " + message;
    }

    @Override
    public List<String> mergeTest(String message) {
        return null;
    }
}
