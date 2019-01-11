package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.film.FilmServiceApi;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 *         2019/1/11 19:47
 */
@RestController
@RequestMapping("/film")
public class FilmController {

    private static final String IMG_PRE = "https://www.chengix.com";

    @Reference(interfaceClass = FilmServiceApi.class)
    private FilmServiceApi filmServiceApi;

    /**
     * 首页
     * API 网关：
     * 1.功能聚合【API聚合】
     * 好处：
     * -  1. 六个接口，一次请求，同一时间节省了五次 HTTP 请求
     * -  2. 同一个接口对外暴露，降低了前后端分离开发的难度和复杂度
     * 坏处:
     * -  一次获取数据过多，容易出现问题
     */
    @GetMapping("/getIndex")
    public ResponseVO getIndex() {

        FilmIndexVo filmIndexVo = new FilmIndexVo();

        // 获取 banner 信息
        filmIndexVo.setBanners(filmServiceApi.getBanners());
        // 获取 正在热映 电影
        filmIndexVo.setHotFilms(filmServiceApi.getHotFilms(true, 8));
        // 获取 即将上映 电影
        filmIndexVo.setSoonFilms(filmServiceApi.getSoonFilms(true, 8));
        // 获取 票房排行榜
        filmIndexVo.setBoxRanking(filmServiceApi.getBoxRanking());
        // 获取 受欢迎 榜单
        filmIndexVo.setExpectRanking(filmServiceApi.getExpectRanking());
        // 获取 前一百 电影
        filmIndexVo.setTop100(filmServiceApi.getTop100());

        return ResponseVO.success(IMG_PRE, filmIndexVo);
    }
}
