package com.cheng.dubbo;

import java.util.List;

/**
 * @author cheng
 *         2019/1/17 16:50
 */
public class MyMock implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        return "抱歉，购买人数过多，请稍后重试";
    }

    @Override
    public String sendMessage2(String message) {
        return "抱歉，购买人数过多，请稍后重试";
    }

    @Override
    public List<String> mergeTest(String message) {
        return null;
    }
}
