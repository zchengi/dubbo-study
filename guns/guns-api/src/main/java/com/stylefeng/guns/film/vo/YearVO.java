package com.stylefeng.guns.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/11 23:02
 */
@Data
public class YearVO implements Serializable {

    private static final long serialVersionUID = -8293273630493778051L;

    private String yearId;
    private String yearName;
    private Boolean isActive;
}
