package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/11 19:57
 */
@Data
public class BannerVO implements Serializable {

    private static final long serialVersionUID = 1098595883192187590L;

    private String bannerId;
    private String bannerAddress;
    private String bannerUrl;
}
