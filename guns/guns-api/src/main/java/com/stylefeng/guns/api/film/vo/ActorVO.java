package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/12 16:55
 */
@Data
public class ActorVO implements Serializable {

    private static final long serialVersionUID = -7838430036007302423L;

    private String imgAddress;
    private String directorName;
    private String roleName;
}
