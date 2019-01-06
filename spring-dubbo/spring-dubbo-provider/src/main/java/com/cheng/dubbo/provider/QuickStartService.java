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
    public String sendMessage(String message) {

        System.out.println("message: " + message);
        return "quick-start-provider-message: " + message;
    }
}
