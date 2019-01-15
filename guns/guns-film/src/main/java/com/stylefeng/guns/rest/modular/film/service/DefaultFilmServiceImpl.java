package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
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
@Service(interfaceClass = FilmServiceAPI.class)
public class DefaultFilmServiceImpl implements FilmServiceAPI {

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

    @Autowired
    private ChengFilmInfoTMapper chengFilmInfoTMapper;

    @Autowired
    private ChengActorTMapper chengActorTMapper;

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
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId) {

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
            Page<ChengFilmT> page;

            // 根据 sortId，组织排序方式
            // 1:热门, 2:时间, 3:评价
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
            }

            // 如果 sourceId, catId, yearId 不为 99，则表示要按照对应的编号查询
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (catId != 99) {
                // #2#4#22# (多个类别)
                String catStr = "%#" + catId + "#%";
                entityWrapper.eq("film_cats", catStr);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }

            List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);

            // 组织 filmInfoList
            filmInfoList = getFilmInfoList(chengFilmTList);
            filmVO.setFilmNum(chengFilmTList.size());

            // 总页数 totalCounts/nums + 1
            // 每页10条，现在有6条 -> 1
            int totalCounts = chengFilmTMapper.selectCount(entityWrapper);
            int totalPage = totalCounts / nums + 1;

            filmVO.setFilmInfo(filmInfoList);
            filmVO.setTotalPage(totalPage);
            filmVO.setNowPage(nowPage);
        }

        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId) {

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
            Page<ChengFilmT> page;

            // 根据 sortId，组织排序方式
            // 1:热门, 2:时间, 3:评价
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
            }

            // 如果 sourceId, catId, yearId 不为 99，则表示要按照对应的编号查询
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (catId != 99) {
                // #2#4#22# (多个类别)
                String catStr = "%#" + catId + "#%";
                entityWrapper.eq("film_cats", catStr);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }

            List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);

            // 组织 filmInfoList
            filmInfoList = getFilmInfoList(chengFilmTList);
            filmVO.setFilmNum(chengFilmTList.size());

            // 总页数 totalCounts/nums + 1
            // 每页10条，现在有6条 -> 1
            int totalCounts = chengFilmTMapper.selectCount(entityWrapper);
            int totalPage = totalCounts / nums + 1;

            filmVO.setFilmInfo(filmInfoList);
            filmVO.setTotalPage(totalPage);
            filmVO.setNowPage(nowPage);
        }

        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int catId, int yearId) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfoList;

        // 经典影片限制条件
        EntityWrapper<ChengFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 3);

        Page<ChengFilmT> page;

        // 根据 sortId，组织排序方式
        // 1:热门, 2:时间, 3:评价
        switch (sortId) {
            case 1:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage, nums, "film_time");
                break;
            case 3:
                page = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
        }

        // 如果 sourceId, catId, yearId 不为 99，则表示要按照对应的编号查询
        if (sourceId != 99) {
            entityWrapper.eq("film_source", sourceId);
        }
        if (catId != 99) {
            // #2#4#22# (多个类别)
            String catStr = "%#" + catId + "#%";
            entityWrapper.eq("film_cats", catStr);
        }
        if (yearId != 99) {
            entityWrapper.eq("film_date", yearId);
        }

        List<ChengFilmT> chengFilmTList = chengFilmTMapper.selectPage(page, entityWrapper);
        // 组织 filmInfoList
        filmInfoList = getFilmInfoList(chengFilmTList);
        filmVO.setFilmNum(chengFilmTList.size());

        // 总页数 totalCounts/nums + 1
        // 每页10条，现在有6条 -> 1
        int totalCounts = chengFilmTMapper.selectCount(entityWrapper);
        int totalPage = totalCounts / nums + 1;

        filmVO.setFilmInfo(filmInfoList);
        filmVO.setTotalPage(totalPage);
        filmVO.setNowPage(nowPage);

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

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {

        // searchType 1:名称, 2:Id
        FilmDetailVO filmDetailVO;
        if (searchType == 1) {
            filmDetailVO = chengFilmTMapper.getFilmDetailByName("%" + searchParam + "%");
        } else {
            filmDetailVO = chengFilmTMapper.getFilmDetailById(searchParam);
        }

        return filmDetailVO;
    }

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

    private ChengFilmInfoT getFilmInfo(String filmId) {

        ChengFilmInfoT chengFilmInfoT = new ChengFilmInfoT();
        chengFilmInfoT.setFilmId(filmId);

        return chengFilmInfoTMapper.selectOne(chengFilmInfoT);
    }
}
