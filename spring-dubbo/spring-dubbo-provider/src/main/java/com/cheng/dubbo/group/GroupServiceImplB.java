package com.cheng.dubbo.group;

import com.cheng.dubbo.ServiceAPI;

import java.util.Collections;
import java.util.List;

/**
 * @author cheng
 *         2019/1/15 22:23
 */
public class GroupServiceImplB implements ServiceAPI {

    @Override
    public String sendMessage(String message) {

        System.out.println("sendMessage group-b: " + message);

        return "quick-start-provider-message group-b: " + message;
    }

    @Override
    public String sendMessage2(String message) {

        System.out.println("sendMessage2: " + message);
        return "quick-start-provider-message2: " + message;
    }

    @Override
    public List<String> mergeTest(String message) {
        String str = "groupB: " + message;
        return Collections.singletonList(str);
    }
}
