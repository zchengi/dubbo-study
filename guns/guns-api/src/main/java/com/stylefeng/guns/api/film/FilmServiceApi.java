package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * @author cheng
 *         2019/1/11 20:20
 */
public interface FilmServiceApi {

    /**
     * 获取 banner
     */
    List<BannerVO> getBanners();

    /**
     * 获取 热映电影
     */
    FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId);

    /**
     * 推荐做法：扩展接口
     */
//    FilmVO getHotFilms(boolean isLimit, int nums...);

    /**
     * 获取 即将上映影片（按照受欢迎程度降序排列）
     */
    FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId);

    /**
     * 获取 经典影片
     */
    FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int catId, int yearId);

    /**
     * 获取 票房排行榜
     */
    List<FilmInfo> getBoxRanking();

    /**
     * 获取 人气排行榜
     */
    List<FilmInfo> getExpectRanking();

    /**
     * 获取 Top100
     */
    List<FilmInfo> getTop100();

    // ---------- 获取影片条件接口 ----------

    /**
     * 分类条件
     */
    List<CatVO> getCatList();

    /**
     * 片源条件
     */
    List<SourceVO> getSourceList();

    /**
     * 年代条件
     */
    List<YearVO> getYearList();

    // ---------- 影片详情查询接口 ----------

    /**
     * 影片Id 或者 影片名称 获取影片信息
     */
    FilmDetailVO getFilmDetail(int searchType, String searchParam);

    /**
     * 影片 描述信息
     */
    FilmDescVO getFilmDesc(String filmId);

    /**
     * 影片 图片信息
     */
    ImgVO getImages(String filmId);

    /**
     * 影片 导演信息
     */
    ActorVO getDirectorInfo(String filmId);

    /**
     * 影片 演员信息
     */
    List<ActorVO> getActors(String filmId);
}
