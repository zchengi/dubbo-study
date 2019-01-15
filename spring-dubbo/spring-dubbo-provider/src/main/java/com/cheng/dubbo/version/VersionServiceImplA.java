package com.cheng.dubbo.version;


import com.cheng.dubbo.ServiceAPI;

import java.util.Collections;
import java.util.List;

/**
 * @author cheng
 *         2019/1/15 00:22
 */
public class VersionServiceImplA implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        System.out.println("message group a version-0.1: " + message);

        return "quickstart-provider-message-group-a version-0.1: " + message;
    }

    @Override
    public String sendMessage2(String message) {
        System.out.println("message2: " + message);

        return "quickstart-provider-message2: " + message;
    }

    @Override
    public List<String> mergeTest(String message) {
        String str = "groupA: " + message;
        return Collections.singletonList(str);
    }
}
