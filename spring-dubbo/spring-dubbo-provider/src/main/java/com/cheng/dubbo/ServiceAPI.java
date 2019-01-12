package com.cheng.dubbo;

/**
 * @author cheng
 *         2018/12/31 23:08
 */
public interface ServiceAPI {

    String sendMessage(String message) throws InterruptedException;

    String sendMessage2(String message) throws InterruptedException;
}
