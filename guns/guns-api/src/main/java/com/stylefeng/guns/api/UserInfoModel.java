package com.stylefeng.guns.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cheng
 *         2019/1/3 23:37
 */
@Getter
@Setter
public class UserInfoModel {

    private Integer uuid;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private int sex;
    private String birthday;
    private String lifeState;
    private String biography;
    private String address;
    private String headAddress;
    private Long beginTime;
    private Long updateTime;

}
