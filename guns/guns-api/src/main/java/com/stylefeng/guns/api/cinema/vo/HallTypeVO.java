package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:40
 */
@Data
public class HallTypeVO implements Serializable {

    private static final long serialVersionUID = -8854534157311119306L;

    private String halltypeId;
    private String halltypeName;
    private boolean isActive;
}
