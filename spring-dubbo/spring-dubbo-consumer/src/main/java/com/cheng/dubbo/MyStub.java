package com.cheng.dubbo;

import java.util.List;

/**
 * @author cheng
 *         2019/1/17 16:34
 */
public class MyStub implements ServiceAPI {

    /**
     * 注入 Proxy 的构造函数
     */
    private final ServiceAPI serviceAPI;

    public MyStub(ServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
    }

    @Override
    public String sendMessage(String message) throws InterruptedException {

        System.out.println("stub sendMessage");

        if ("123".equals(message)) {
            return "抱歉，该值不能被接收";
        } else {
            message = "stub message -> " + message;
            return this.serviceAPI.sendMessage(message);
        }
    }

    @Override
    public String sendMessage2(String message) throws InterruptedException {
        System.out.println("stub sendMessage2");
        return this.serviceAPI.sendMessage2(message);
    }

    @Override
    public List<String> mergeTest(String message) {
        System.out.println("stub mergeTest");
        return this.serviceAPI.mergeTest(message);
    }
}
