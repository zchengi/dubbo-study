package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/15 18:15
 */
@Data
public class OrderQueryVO implements Serializable {

    private static final long serialVersionUID = -3134779484244974046L;

    private String cinemaId;
    private String filmPrice;
}
