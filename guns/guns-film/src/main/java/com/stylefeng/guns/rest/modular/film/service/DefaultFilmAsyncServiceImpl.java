package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.film.FilmAsyncServiceApi;
import com.stylefeng.guns.api.film.vo.ActorVO;
import com.stylefeng.guns.api.film.vo.FilmDescVO;
import com.stylefeng.guns.api.film.vo.ImgVO;
import com.stylefeng.guns.rest.common.persistence.dao.ChengActorTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.ChengFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.ChengActorT;
import com.stylefeng.guns.rest.common.persistence.model.ChengFilmInfoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cheng
 *         2019/1/11 21:08
 */
@Component
@Service(interfaceClass = FilmAsyncServiceApi.class)
public class DefaultFilmAsyncServiceImpl implements FilmAsyncServiceApi {

    @Autowired
    private ChengFilmInfoTMapper chengFilmInfoTMapper;

    @Autowired
    private ChengActorTMapper chengActorTMapper;

    @Override
    public FilmDescVO getFilmDesc(String filmId) {

        FilmDescVO filmDescVO = new FilmDescVO();

        ChengFilmInfoT chengFilmInfoT = getFilmInfo(filmId);
        filmDescVO.setBiography(chengFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);

        return filmDescVO;
    }

    @Override
    public ImgVO getImages(String filmId) {

        ImgVO imgVO = new ImgVO();

        ChengFilmInfoT chengFilmInfoT = getFilmInfo(filmId);

        // filmImgStr: 五个以逗号分隔的链接
        String filmImgStr = chengFilmInfoT.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDirectorInfo(String filmId) {

        ActorVO actorVO = new ActorVO();

        ChengFilmInfoT chengFilmInfoT = getFilmInfo(filmId);
        ChengActorT chengActorT = chengActorTMapper.selectById(chengFilmInfoT.getDirectorId());

        actorVO.setImgAddress(chengActorT.getActorImg());
        actorVO.setDirectorName(chengActorT.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        return chengActorTMapper.getActors(filmId);
    }

    private ChengFilmInfoT getFilmInfo(String filmId) {

        ChengFilmInfoT chengFilmInfoT = new ChengFilmInfoT();
        chengFilmInfoT.setFilmId(filmId);

        return chengFilmInfoTMapper.selectOne(chengFilmInfoT);
    }
}
