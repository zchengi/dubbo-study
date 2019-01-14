package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:39
 */
@Data
public class BrandVO implements Serializable {

    private static final long serialVersionUID = -8025357458823134973L;

    private String brandId;
    private String brandName;
    private boolean isActive;
}
