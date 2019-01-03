package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.UserAPI;
import com.stylefeng.guns.api.UserInfoModel;
import com.stylefeng.guns.api.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 *         2019/1/2 20:36
 */
@Slf4j
@Component
@Service(interfaceClass = UserAPI.class)
public class UserImpl implements UserAPI {

    @Override
    public int login(String username, String password) {
        log.info("this is user service,username: {}, password: {}", username, password);
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        return null;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        return null;
    }
}
