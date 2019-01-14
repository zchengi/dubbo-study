package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:37
 */
@Data
public class CinemaVO implements Serializable {

    private static final long serialVersionUID = -4872299465650479434L;

    private String uuid;
    private String cinemaName;
    private String address;
    private String minimumPrice;
}
