package com.stylefeng.guns.api;

/**
 * @author cheng
 *         2019/1/2 20:35
 */
public interface UserAPI {

    /**
     * 用户登录
     *
     * @return userId
     */
    int login(String username, String password);

    /**
     * 用户注册
     */
    boolean register(UserModel userModel);

    /**
     * 用户是否存在
     */
    boolean checkUsername(String username);

    /**
     * 用户信息查询
     */
    UserInfoModel getUserInfo(int uuid);

    /**
     * 修改用户信息
     */
    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
