package com.cheng.dubbo;

import java.util.List;

/**
 * @author cheng
 *         2018/12/31 23:08
 */
public interface ServiceAPI {

    String sendMessage(String message);

    String sendMessage2(String message);

    List<String> mergeTest(String message);
}
