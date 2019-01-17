package com.cheng.dubbo.consumer.transaction;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cheng.dubbo.ServiceAPI;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 *         2019/1/17 21:12
 */
@Component
public class TransactionConsumer {

    @Reference(interfaceClass = ServiceAPI.class, check = false)
//    @Autowired
    private ServiceAPI serviceAPI;

    @Compensable(confirmMethod = "confirmSendMessage",
            cancelMethod = "cancelSendMessage",
            asyncConfirm = true)
    public void sendMessage(String message) {
//        System.out.println("this is consumer sendMessage message: " + message);

        // 测试业务
        serviceAPI.saveOrder("001", message, "5");

        serviceAPI.isTrueSeats(message);

        serviceAPI.isNotSold(message);
    }

    public void confirmSendMessage(String message) {
        System.out.println("this is consumer confirmSendMessage message: " + message);
    }

    public void cancelSendMessage(String message) {
        System.out.println("this is consumer cancelSendMessage message: " + message);
    }
}