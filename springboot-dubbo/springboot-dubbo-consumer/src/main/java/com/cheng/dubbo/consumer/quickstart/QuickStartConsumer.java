package com.cheng.dubbo.consumer.quickstart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cheng.dubbo.ServiceAPI;

/**
 * @author cheng
 *         2019/1/1 22:21
 */
//@Component
public class QuickStartConsumer {

    @Reference(interfaceClass = ServiceAPI.class)
    private ServiceAPI serviceAPI;

    public void sendMessage(String message) {
        System.out.println(serviceAPI.sendMessage(message));
    }

}
