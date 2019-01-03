package com.stylefeng.guns.rest.common;

/**
 * ThreadLocal 作为 session 存储用户信息
 *
 * @author cheng
 *         2019/1/3 23:58
 */
public class CurrentUser {

    /**
     * 线程绑定的存储空间
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveUserId(String userId) {
        THREAD_LOCAL.set(userId);
    }

    public static String getCurrentUser() {
        return THREAD_LOCAL.get();
    }

    /*
    private static final ThreadLocal<UserInfoModel> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveUserInfo(UserInfoModel userInfoModel) {
        THREAD_LOCAL.set(userInfoModel);
    }

    public static UserInfoModel getCurrentUser() {
        return THREAD_LOCAL.get();
    }*/
}
