package com.cheng.dubbo.provider.transaction;

import com.alibaba.dubbo.config.annotation.Service;
import com.cheng.dubbo.ServiceAPI;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 *         2019/1/17 21:03
 */
@Component
@Service(interfaceClass = ServiceAPI.class)
public class TransactionServiceImpl implements ServiceAPI {

    @Override
    @Compensable(confirmMethod = "confirmSendMessage",
            cancelMethod = "cancelSendMessage",
            transactionContextEditor = DubboTransactionContextEditor.class)
    public String sendMessage(String message) {

        System.out.println("this is sendMessage try message: " + message);

        if ("123".equals(message)) {
            throw new NullPointerException();
        }

        return "tcc-provider-message: " + message;
    }

    @Override
    @Compensable(confirmMethod = "confirmIsTrueSeats",
            cancelMethod = "cancelIsTrueSeats",
            transactionContextEditor = DubboTransactionContextEditor.class)
    public boolean isTrueSeats(String seats) {

        if ("1,2,3".equals(seats)) {
            throw new IllegalArgumentException("isTrueSeats error");
        }

        return true;
    }

    @Override
    @Compensable(confirmMethod = "confirmIsNotSold",
            cancelMethod = "cancelIsNotSold",
            transactionContextEditor = DubboTransactionContextEditor.class)
    public boolean isNotSold(String seats) {

        if ("4,5".equals(seats)) {
            throw new IllegalArgumentException("isNotSold error");
        }

        return true;
    }

    /**
     * 重点: 幂等性问题
     */
    @Override
    @Compensable(confirmMethod = "confirmSaveOrder",
            cancelMethod = "cancelSaveOrder",
            transactionContextEditor = DubboTransactionContextEditor.class)
    public String saveOrder(String fieldId, String seats, String seatsNum) {
        System.out.println("创建一个待支付状态的订单");
        return "";
    }

    public String confirmSaveOrder(String fieldId, String seats, String seatsNum) {
        System.out.println("将订单修改为支付中");
        return "";
    }

    public String cancelSaveOrder(String fieldId, String seats, String seatsNum) {
        System.out.println("将订单修改为已关闭");
        return "";
    }

    public String confirmSendMessage(String message) {
        System.out.println("this is confirmSendMessage message: " + message);
        return "tcc-provider-message=" + message;
    }

    public String cancelSendMessage(String message) {
        System.out.println("this is cancelSendMessage message: " + message);
        return "tcc-provider-message=" + message;
    }

    public boolean confirmIsTrueSeats(String seats) {
        System.out.println("this is confirmIsTrueSeats");
        return true;
    }

    public boolean cancelIsTrueSeats(String seats) {
        System.out.println("this is cancelIsTrueSeats");
        return true;
    }

    public boolean confirmIsNotSold(String seats) {
        System.out.println("this is confirmIsNotSold");
        return true;
    }

    public boolean cancelIsNotSold(String seats) {
        System.out.println("this is cancelIsNotSold");
        return true;
    }
}
