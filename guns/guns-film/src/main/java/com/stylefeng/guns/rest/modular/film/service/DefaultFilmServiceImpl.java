package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.FilmServiceApi;
import com.stylefeng.guns.film.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
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

    @Autowired
    private ChengCatDictTMapper chengCatDictTMapper;

    @Autowired
    private ChengSourceDictTMapper chengSourceDictTMapper;

    @Autowired
    private ChengYearDictTMapper chengYearDictTMapper;

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
            // 组织 filmInfo
            filmVO.setFilmInfo(getFilmInfoList(chengFilmTList));
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
            // 组织 filmInfo
            filmVO.setFilmInfo(getFilmInfoList(chengFilmTList));
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

    @Override
    public List<CatVO> getCatList() {

        List<CatVO> catVOList = new ArrayList<>();
        List<ChengCatDictT> chengCatDictTList = chengCatDictTMapper.selectList(null);

        // 实体对象 ChengCatDictT -> 业务对象 CatVO
        chengCatDictTList.forEach(chengCatDictT -> {
            CatVO catVO = new CatVO();

            catVO.setCatId(String.valueOf(chengCatDictT.getUuid()));
            catVO.setCatName(chengCatDictT.getShowName());

            catVOList.add(catVO);
        });

        return catVOList;
    }

    @Override
    public List<SourceVO> getSourceList() {

        List<SourceVO> sourceVOList = new ArrayList<>();
        List<ChengSourceDictT> chengSourceDictTList = chengSourceDictTMapper.selectList(null);

        // 实体对象 ChengSourceDictT -> 业务对象 SourceVO
        chengSourceDictTList.forEach(chengCatDictT -> {
            SourceVO sourceVO = new SourceVO();

            sourceVO.setSourceId(String.valueOf(chengCatDictT.getUuid()));
            sourceVO.setSourceName(chengCatDictT.getShowName());

            sourceVOList.add(sourceVO);
        });

        return sourceVOList;
    }

    @Override
    public List<YearVO> getYearList() {

        List<YearVO> yearVOList = new ArrayList<>();
        List<ChengYearDictT> sourceDictTList = chengYearDictTMapper.selectList(null);

        // 实体对象 ChengYearDictT -> 业务对象 YearVO
        sourceDictTList.forEach(chengCatDictT -> {
            YearVO yearVO = new YearVO();

            yearVO.setYearId(String.valueOf(chengCatDictT.getUuid()));
            yearVO.setYearName(chengCatDictT.getShowName());

            yearVOList.add(yearVO);
        });

        return yearVOList;
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
            filmInfo.setFilmId(chengFilmT.getUuid() + "");
            filmInfo.setExpectNum(chengFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(chengFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(chengFilmT.getFilmTime()));

            filmInfoList.add(filmInfo);
        });

        return filmInfoList;
    }
}
