package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.ActorVO;
import com.stylefeng.guns.api.film.vo.FilmDescVO;
import com.stylefeng.guns.api.film.vo.ImgVO;

import java.util.List;

/**
 * @author cheng
 *         2019/1/11 20:20
 */
public interface FilmAsyncServiceAPI {

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
