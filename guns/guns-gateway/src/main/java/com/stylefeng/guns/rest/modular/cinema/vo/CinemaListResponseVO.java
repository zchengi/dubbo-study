package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaVO;
import lombok.Data;

import java.util.List;

/**
 * @author cheng
 *         2019/1/14 14:35
 */
@Data
public class CinemaListResponseVO {

    private List<CinemaVO> cinemas;
}
