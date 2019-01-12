package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/12 16:55
 */
@Data
public class ImgVO implements Serializable {

    private static final long serialVersionUID = -487953289085084318L;

    private String mainImg;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
}
