package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/11 23:02
 */
@Data
public class SourceVO implements Serializable {

    private static final long serialVersionUID = -8293273630493778051L;

    private String sourceId;
    private String sourceName;
    private Boolean isActive;
}
