package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.film.FilmAsyncServiceApi;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRequestVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author cheng
 *         2019/1/11 19:47
 */
@RestController
@RequestMapping("/film")
public class FilmController {

    private static final String IMG_PRE = "https://www.chengix.com";

    @Reference(interfaceClass = FilmServiceApi.class, check = false)
    private FilmServiceApi filmServiceApi;

    @Reference(interfaceClass = FilmAsyncServiceApi.class, async = true, check = false)
    private FilmAsyncServiceApi filmAsyncServiceApi;

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

        FilmIndexVO filmIndexVO = new FilmIndexVO();

        // 获取 banner 信息
        filmIndexVO.setBanners(filmServiceApi.getBanners());
        // 获取 正在热映 电影
        filmIndexVO.setHotFilms(filmServiceApi.getHotFilms(true, 8, 1, 1, 99, 99, 99));
        // 获取 即将上映 电影
        filmIndexVO.setSoonFilms(filmServiceApi.getSoonFilms(true, 8, 1, 1, 99, 99, 99));
        // 获取 票房前十
        filmIndexVO.setBoxRanking(filmServiceApi.getBoxRanking());
        // 获取 受欢迎 榜单
        filmIndexVO.setExpectRanking(filmServiceApi.getExpectRanking());
        // 获取 前一百 电影
        filmIndexVO.setTop100(filmServiceApi.getTop100());

        return ResponseVO.success(IMG_PRE, filmIndexVO);
    }

    /**
     * 条件查询列表
     */
    @GetMapping("/getConditionList")
    public ResponseVO getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {

        FilmConditionVO filmConditionVO = new FilmConditionVO();

        // 类型集合

        // 判断集合中是否有与传入 id 对应的值
        boolean flag = false;
        List<CatVO> catVoList = filmServiceApi.getCatList();
        CatVO catVOId99 = null;
        for (CatVO catVO : catVoList) {

            // 判断集合是否存在 id，如果存在，则将对应的实体变成 active 状态
            // 如果传入的 id 在集合中不存在就会出现 bug，设置 flag 解决
            // 如:集合: 1,2,3,99,4,5 ; 传入: 6

            // 不判断 【全部】
            if ("99".equals(catVO.getCatId())) {
                catVOId99 = catVO;
                catVOId99.setIsActive(false);
                continue;
            }

            if (catId.equals(catVO.getCatId())) {
                flag = true;
                catVO.setIsActive(true);
            } else {
                catVO.setIsActive(false);
            }
        }

        // 如果没有 id ，则默认将【全部】变为 Active 状态
        if (catVOId99 != null && !flag) {
            catVOId99.setIsActive(true);
        }

        // 片源集合
        flag = false;
        List<SourceVO> sourceVOList = filmServiceApi.getSourceList();
        SourceVO sourceVOId99 = null;
        for (SourceVO sourceVO : sourceVOList) {

            if ("99".equals(sourceVO.getSourceId())) {
                sourceVOId99 = sourceVO;
                sourceVOId99.setIsActive(false);
                continue;
            }

            if (sourceId.equals(sourceVO.getSourceId())) {
                flag = true;
                sourceVO.setIsActive(true);
            } else {
                sourceVO.setIsActive(false);
            }
        }

        if (sourceVOId99 != null && !flag) {
            sourceVOId99.setIsActive(true);
        }

        // 年代集合
        flag = false;
        List<YearVO> yearVOList = filmServiceApi.getYearList();
        YearVO yearVOId99 = null;
        for (YearVO yearVO : yearVOList) {

            if ("99".equals(yearVO.getYearId())) {
                yearVOId99 = yearVO;
                yearVOId99.setIsActive(false);
                continue;
            }

            if (yearId.equals(yearVO.getYearId())) {
                flag = true;
                yearVO.setIsActive(true);
            } else {
                yearVO.setIsActive(false);
            }
        }

        if (yearVOId99 != null && !flag) {
            yearVOId99.setIsActive(true);
        }

        filmConditionVO.setCatInfo(catVoList);
        filmConditionVO.setSourceInfo(sourceVOList);
        filmConditionVO.setYearInfo(yearVOList);

        return ResponseVO.success(filmConditionVO);
    }

    /**
     * 影片查询
     */
    @GetMapping("/getFilms")
    public ResponseVO getFilms(FilmRequestVO filmRequestVO) {

        FilmVO filmVO;

        // 根据 showType 判断影片查询类型
        switch (filmRequestVO.getShowType()) {
            case 1:
                filmVO = filmServiceApi.getHotFilms(
                        false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 2:
                filmVO = filmServiceApi.getSoonFilms(
                        false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 3:
                filmVO = filmServiceApi.getClassicFilms(
                        filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(), filmRequestVO.getCatId());
                break;
            default:
                filmVO = filmServiceApi.getHotFilms(
                        false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
        }
        // 根据 sortId 排序
        // 添加查询条件
        // 判断当前第几页
        return ResponseVO.success(IMG_PRE, filmVO.getFilmInfo(), filmVO.getNowPage(), filmVO.getTotalPage());
    }

    /**
     * 影片详情
     */
    @GetMapping("/films/{searchParam}")
    public ResponseVO films(int searchType,
                            @PathVariable String searchParam) throws ExecutionException, InterruptedException {

        // 根据 searchType，判断查询类型
        FilmDetailVO filmDetail = filmServiceApi.getFilmDetail(searchType, searchParam);

        if (filmDetail == null) {
            return ResponseVO.serviceFail("没有可查询的影片");
        } else if (filmDetail.getFilmId() == null || filmDetail.getFilmId().trim().length() == 0) {
            return ResponseVO.serviceFail("没有可查询的影片");
        }

        String filmId = filmDetail.getFilmId();
        // 查询影片 详细信息 -> Dubbo 异步获取

        // - 描述信息
        filmAsyncServiceApi.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();

        // - 图片信息
        filmAsyncServiceApi.getImages(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();

        // - 演员信息
        filmAsyncServiceApi.getDirectorInfo(filmId);
        Future<ActorVO> directorVOFuture = RpcContext.getContext().getFuture();

        // - 演员信息
        filmAsyncServiceApi.getActors(filmId);
        Future<List<ActorVO>> actorsFuture = RpcContext.getContext().getFuture();

        // ActorRequestVO
        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actorsFuture.get());
        actorRequestVO.setDirector(directorVOFuture.get());

        // InfoRequestVO
        InfoRequestVO infoRequestVO = new InfoRequestVO();
        infoRequestVO.setFilmId(filmId);
        infoRequestVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequestVO.setImgVO(imgVOFuture.get());
        infoRequestVO.setActors(actorRequestVO);

        // Info04
        filmDetail.setInfo04(infoRequestVO);

        return ResponseVO.success(IMG_PRE, filmDetail);
    }
}
