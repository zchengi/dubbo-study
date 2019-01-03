package com.stylefeng.guns.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cheng
 *         2019/1/3 23:32
 */
@Getter
@Setter
public class UserModel {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
}
