package com.stylefeng.guns.film.vo;

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
    private List<FilmInfo> filmInfoList;
}
