package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:42
 */
@Data
public class FilmFieldVO  implements Serializable {

    private static final long serialVersionUID = -6201125514161434833L;

    private String fieldId;
    private String beginTime;
    private String endTime;
    private String language;
    private String hallName;
    private String price;
}
