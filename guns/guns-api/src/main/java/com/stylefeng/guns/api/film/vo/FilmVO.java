package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author cheng
 *         2019/1/11 20:09
 */
@Data
public class FilmVO implements Serializable {

    private static final long serialVersionUID = -289334859938117907L;

    private Integer filmNum;
    private Integer nowPage;
    private Integer totalPage;

    private List<FilmInfo> filmInfo;
}
