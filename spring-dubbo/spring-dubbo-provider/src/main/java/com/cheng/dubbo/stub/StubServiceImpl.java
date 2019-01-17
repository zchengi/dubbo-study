package com.cheng.dubbo.stub;

import com.cheng.dubbo.ServiceAPI;

import java.util.List;

/**
 * @author cheng
 *         2018/12/31 23:11
 */
public class StubServiceImpl implements ServiceAPI {

    @Override
    public String sendMessage(String message) {

        System.out.println("stub sendMessage: " + message);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "stub: " + message;
    }

    @Override
    public String sendMessage2(String message) {

        System.out.println("sendMessage2: " + message);
        return "quick-start-provider-message2: " + message;
    }

    @Override
    public List<String> mergeTest(String message) {
        return null;
    }
}
