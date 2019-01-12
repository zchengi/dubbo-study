package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.util.List;

/**
 * @author cheng
 *         2019/1/12 17:20
 */
@Data
public class ActorRequestVO {

    private ActorVO director;

    private List<ActorVO> actors;
}
