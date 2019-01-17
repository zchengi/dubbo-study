package com.cheng.dubbo.provider.quickstart;

import com.cheng.dubbo.ServiceAPI;

/**
 * @author cheng
 *         2019/1/1 22:21
 */
//@Component
//@Service(interfaceClass = ServiceAPI.class)
public class QuickStartServiceImpl implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        return "quickstart-provider-message: " + message;
    }

    @Override
    public boolean isTrueSeats(String seats) {
        return false;
    }

    @Override
    public boolean isNotSold(String seats) {
        return false;
    }

    @Override
    public String saveOrder(String fieldId, String seats, String seatsNum) {
        return null;
    }
}
