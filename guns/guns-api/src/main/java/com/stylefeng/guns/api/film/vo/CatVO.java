package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/11 23:02
 */
@Data
public class CatVO implements Serializable {

    private static final long serialVersionUID = -7096877165914607583L;

    private String catId;
    private String catName;
    private Boolean isActive;
}
