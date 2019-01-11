package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.film.vo.CatVO;
import com.stylefeng.guns.film.vo.SourceVO;
import com.stylefeng.guns.film.vo.YearVO;
import lombok.Data;

import java.util.List;

/**
 * @author cheng
 *         2019/1/11 23:17
 */
@Data
public class FilmConditionVO {

    private List<CatVO> catInfo;
    private List<SourceVO> sourceInfo;
    private List<YearVO> yearInfo;
}
