package com.stylefeng.guns.api.user.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/3 23:37
 */
@Getter
@Setter
public class UserInfoModel implements Serializable {

    private static final long serialVersionUID = 5951623536668025518L;

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
