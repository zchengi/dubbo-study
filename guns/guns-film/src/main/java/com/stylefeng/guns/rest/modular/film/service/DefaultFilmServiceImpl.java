package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.FilmServiceApi;
import com.stylefeng.guns.film.vo.BannerVO;
import com.stylefeng.guns.film.vo.FilmInfo;
import com.stylefeng.guns.film.vo.FilmVO;
import com.stylefeng.guns.rest.common.persistence.dao.ChengBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.ChengFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.ChengBannerT;
import com.stylefeng.guns.rest.common.persistence.model.ChengFilmT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 *         2019/1/11 21:08
 */
@Component
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultFilmServiceImpl implements FilmServiceApi {

    @Autowired
    private ChengBannerTMapper chengBannerTMapper;

    @Autowired
    private ChengFilmTMapper chengFilmTMapper;

    @Override
    public List<BannerVO> getBanners() {

        List<BannerVO> res = new ArrayList<>();
        List<ChengBannerT> chengBannerTList = chengBannerTMapper.selectList(null);

        chengBannerTList.forEach(chengBannerT -> {
            BannerVO bannerVO = new BannerVO();
            // 对象拷贝
            BeanUtils.copyProperties(chengBannerT, bannerVO);
            // 因为 uuid 类型不同，所以只能自己赋值
            bannerVO.setBannerId(String.valueOf(chengBannerT.getUuid()));
            res.add(bannerVO);
        });

        return res;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfoList;
        EntityWrapper<ChengFilmT> entityWrapper = new EntityWrapper<>();

        // 限制内容为 热映影片
        entityWrapper.eq("film_status", "1");

        // 判断是否是首页需要
        if (isLimit) {
            // 是: 限制条数
            Page<ChengFilmT> page = new Page<>(1, nums);
            List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);

            filmVO.setFilmNum(chengFilmTList.size());
            // 组织 filmInfoList
            filmVO.setFilmInfoList(getFilmInfoList(chengFilmTList));
        } else {
            // 否: 为列表页

        }

        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfoList;
        EntityWrapper<ChengFilmT> entityWrapper = new EntityWrapper<>();

        // 限制内容为 热映影片
        entityWrapper.eq("film_status", "1");

        // 判断是否是首页需要
        if (isLimit) {
            // 是: 限制条数
            Page<ChengFilmT> page = new Page<>(2, nums);
            List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);

            filmVO.setFilmNum(chengFilmTList.size());
            // 组织 filmInfoList
            filmVO.setFilmInfoList(getFilmInfoList(chengFilmTList));
        } else {
            // 否: 为列表页

        }

        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {

        // 条件: 正在上映, 票房前十

        EntityWrapper<ChengFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<ChengFilmT> page = new Page<>(1, 10, "film_box_office");
        List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);

        return getFilmInfoList(chengFilmTList);
    }

    @Override
    public List<FilmInfo> getExpectRanking() {

        // 条件: 即将上映, 预售前十

        EntityWrapper<ChengFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        Page<ChengFilmT> page = new Page<>(1, 10, "film_preSaleNum");
        List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);

        return getFilmInfoList(chengFilmTList);
    }

    @Override
    public List<FilmInfo> getTop100() {

        // 条件: 正在上映, 评分前十

        EntityWrapper<ChengFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<ChengFilmT> page = new Page<>(1, 10, "film_score");
        List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);

        return getFilmInfoList(chengFilmTList);
    }

    /**
     * ChengFilmT -> FilmInfo
     */
    private List<FilmInfo> getFilmInfoList(List<ChengFilmT> chengFilmTList) {

        List<FilmInfo> filmInfoList = new ArrayList<>();

        chengFilmTList.forEach(chengFilmT -> {
            FilmInfo filmInfo = new FilmInfo();

            filmInfo.setScore(chengFilmT.getFilmScore());
            filmInfo.setImgAddress(chengFilmT.getImgAddress());
            filmInfo.setFilmType(chengFilmT.getFilmType());
            filmInfo.setFilmScore(chengFilmT.getFilmScore());
            filmInfo.setFilmName(chengFilmT.getFilmName());
            filmInfo.setFilmId(chengFilmT.getUuid()+"");
            filmInfo.setExpectNum(chengFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(chengFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(chengFilmT.getFilmTime()));

            filmInfoList.add(filmInfo);
        });

        return filmInfoList;
    }
}
