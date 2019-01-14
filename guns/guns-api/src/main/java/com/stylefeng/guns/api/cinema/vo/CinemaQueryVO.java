package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:15
 */
@Data
public class CinemaQueryVO implements Serializable {

    private static final long serialVersionUID = 5817044547503038407L;

    private Integer brandId = 99;
    private Integer districtId = 99;
    private Integer hallType = 99;
    private Integer pageSize = 12;
    private Integer nowPage = 1;
}
