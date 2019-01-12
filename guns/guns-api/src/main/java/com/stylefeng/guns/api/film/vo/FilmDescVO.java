package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/12 16:54
 */
@Data
public class FilmDescVO implements Serializable {

    private static final long serialVersionUID = -4330743305812566104L;

    private String biography;
    private String filmId;
}
