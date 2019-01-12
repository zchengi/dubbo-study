package com.stylefeng.guns.api.film.vo;

import lombok.Data;

/**
 * @author cheng
 *         2019/1/12 17:21
 */
@Data
public class InfoRequestVO {

    private String biography;
    private ActorRequestVO actors;
    private ImgVO imgVO;
    private String filmId;
}
