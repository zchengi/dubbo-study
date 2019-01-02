package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.UserAPI;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 *         2019/1/2 20:36
 */
@Component
@Service(interfaceClass = UserAPI.class)
public class UserImpl implements UserAPI {

    @Override
    public Boolean login(String username, String password) {
        return true;
    }
}
