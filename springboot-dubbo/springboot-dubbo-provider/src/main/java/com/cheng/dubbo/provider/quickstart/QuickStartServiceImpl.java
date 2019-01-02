package com.cheng.dubbo.provider.quickstart;

import com.alibaba.dubbo.config.annotation.Service;
import com.cheng.dubbo.ServiceAPI;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 *         2019/1/1 22:21
 */
@Component
@Service(interfaceClass = ServiceAPI.class)
public class QuickStartServiceImpl implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        return "quickstart-provider-message=" + message;
    }
}
