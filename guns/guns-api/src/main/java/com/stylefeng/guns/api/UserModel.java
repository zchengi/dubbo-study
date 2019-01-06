package com.stylefeng.guns.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/3 23:32
 */
@Getter
@Setter
public class UserModel implements Serializable {

    private static final long serialVersionUID = -475194444794969176L;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
}
