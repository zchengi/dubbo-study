package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:39
 */
@Data
public class CinemaInfoVO implements Serializable {

    private static final long serialVersionUID = 7158058319077621068L;

    private String cinemaId;
    private String imgUrl;
    private String cinemaName;
    private String cinemaAdress;
    private String cinemaPhone;
}
