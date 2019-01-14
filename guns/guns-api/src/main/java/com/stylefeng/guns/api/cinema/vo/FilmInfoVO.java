package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cheng
 *         2019/1/13 22:42
 */
@Data
public class FilmInfoVO implements Serializable {

    private static final long serialVersionUID = -8576885479845365066L;

    private String filmId;
    private String filmName;
    private String filmLength;
    private String filmType;
    private String filmCats;
    private String actors;
    private String imgAddress;
    private List<FilmFieldVO> filmFields;
}
