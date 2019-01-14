package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:38
 */
@Data
public class AreaVO implements Serializable {

    private static final long serialVersionUID = 3156706137052862985L;

    private String areaId;
    private String areaName;
    private boolean isActive;
}
