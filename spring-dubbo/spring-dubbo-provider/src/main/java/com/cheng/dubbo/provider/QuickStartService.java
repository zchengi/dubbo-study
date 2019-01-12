package com.cheng.dubbo.provider;

import com.cheng.dubbo.ServiceAPI;
import org.springframework.stereotype.Service;

/**
 * @author cheng
 *         2018/12/31 23:11
 */
@Service
public class QuickStartService implements ServiceAPI {

    @Override
    public String sendMessage(String message) throws InterruptedException {

        System.out.println("sendMessage: " + message);

        Thread.sleep(1000);

        return "quick-start-provider-message: " + message;
    }

    @Override
    public String sendMessage2(String message) throws InterruptedException {

        System.out.println("sendMessage2: " + message);

        Thread.sleep(2000);

        return "quick-start-provider-message2: " + message;
    }
}
