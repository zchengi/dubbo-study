package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceApi;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.ChengAreaDictT;
import com.stylefeng.guns.rest.common.persistence.model.ChengBrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.ChengCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.ChengHallDictT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 *         2019/1/13 22:48
 */
@Component
@Service(interfaceClass = CinemaServiceApi.class, executes = 10)
public class DefaultCinemaServiceImpl implements CinemaServiceApi {

    @Autowired
    private ChengAreaDictTMapper chengAreaDictTMapper;

    @Autowired
    private ChengBrandDictTMapper chengBrandDictTMapper;

    @Autowired
    private ChengCinemaTMapper chengCinemaTMapper;

    @Autowired
    private ChengFieldTMapper chengFieldTMapper;

    @Autowired
    private ChengHallDictTMapper chengHallDictTMapper;

    @Autowired
    private ChengHallFilmInfoTMapper chengHallFilmInfoTMapper;

    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {

        // 业务实体集合
        List<CinemaVO> cinemaVOList = new ArrayList<>();

        Page<ChengCinemaT> page = new Page<>(cinemaQueryVO.getNowPage(), cinemaQueryVO.getPageSize());
        // 判断查询条件 -> brandId, districtId, hallType 是否为 99
        EntityWrapper<ChengCinemaT> entityWrapper = new EntityWrapper<>();

        if (cinemaQueryVO.getBrandId() != 99) {
            entityWrapper.eq("brand_id", cinemaQueryVO.getBrandId());
        }

        if (cinemaQueryVO.getDistrictId() != 99) {
            entityWrapper.eq("area_id", cinemaQueryVO.getDistrictId());
        }

        if (cinemaQueryVO.getHallType() != 99) {
            // %#3#%
            entityWrapper.eq("hall_ids", "%#+" + cinemaQueryVO.getHallType() + "+%#");
        }

        // 数据实体 -> 业务实体
        List<ChengCinemaT> chengCinemaTList = chengCinemaTMapper.selectPage(page, entityWrapper);
        chengCinemaTList.forEach(chengCinemaT -> {
            CinemaVO cinemaVO = new CinemaVO();

            cinemaVO.setAddress(chengCinemaT.getCinemaAddress());
            cinemaVO.setCinemaName(chengCinemaT.getCinemaName());
            cinemaVO.setMinimumPrice(String.valueOf(chengCinemaT.getMinimumPrice()));
            cinemaVO.setUuid(String.valueOf(chengCinemaT.getUuid()));

            cinemaVOList.add(cinemaVO);
        });

        // 影院列表总数
        int count = chengCinemaTMapper.selectCount(entityWrapper);

        // 组织返回对象
        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemaVOList);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setTotal(count);

        return result;
    }

    @Override
    public List<BrandVO> getBrands(int brandId) {

        boolean flag = false;
        List<BrandVO> brandVOList = new ArrayList<>();

        // 判断 brandId 是否存在
        ChengBrandDictT chengBrandDictT = chengBrandDictTMapper.selectById(brandId);

        // 判断 brandId 是否等于 99
        if (brandId == 99 || chengBrandDictT == null || chengBrandDictT.getUuid() == null) {
            flag = true;
        }

        // 查询【所有】列表
        List<ChengBrandDictT> chengBrandDictTList = chengBrandDictTMapper.selectList(null);
        // 判断 flag 如果为 true，将 99 置为 isActive
        for (ChengBrandDictT brand : chengBrandDictTList) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandId(String.valueOf(brand.getUuid()));
            brandVO.setBrandName(brand.getShowName());

            // flag = true，需要 99
            // flag = false，匹配到的内容为 active
            if (flag) {
                if (brand.getUuid() == 99) {
                    brandVO.setActive(true);
                }
            } else {
                if (brand.getUuid() == brandId) {
                    brandVO.setActive(true);
                }
            }

            brandVOList.add(brandVO);
        }

        return brandVOList;
    }

    @Override
    public List<AreaVO> getAreas(int areaId) {

        boolean flag = false;
        List<AreaVO> areaVOList = new ArrayList<>();

        // 判断 areaId 是否存在
        ChengAreaDictT chengAreaDictT = chengAreaDictTMapper.selectById(areaId);

        // 判断 areaId 是否等于 99
        if (areaId == 99 || chengAreaDictT == null || chengAreaDictT.getUuid() == null) {
            flag = true;
        }

        // 查询【所有】列表
        List<ChengAreaDictT> chengAreaDictTList = chengAreaDictTMapper.selectList(null);
        // 判断 flag 如果为 true，将 99 置为 isActive
        for (ChengAreaDictT area : chengAreaDictTList) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaId(String.valueOf(area.getUuid()));
            areaVO.setAreaName(area.getShowName());

            // flag = true，需要 99
            // flag = false，匹配到的内容为 active
            if (flag) {
                if (area.getUuid() == 99) {
                    areaVO.setActive(true);
                }
            } else {
                if (area.getUuid() == areaId) {
                    areaVO.setActive(true);
                } else {
                    areaVO.setActive(false);
                }
            }

            areaVOList.add(areaVO);
        }

        return areaVOList;
    }

    @Override
    public List<HallTypeVO> getHallTypes(int hallType) {

        boolean flag = false;
        List<HallTypeVO> hallTypeVOList = new ArrayList<>();

        // 判断 hallType 是否存在
        ChengHallDictT chengHallDictT = chengHallDictTMapper.selectById(hallType);

        // 判断 hallType 是否等于 99
        if (hallType == 99 || chengHallDictT == null || chengHallDictT.getUuid() == null) {
            flag = true;
        }

        // 查询【所有】列表
        List<ChengHallDictT> chengBrandDictTList = chengHallDictTMapper.selectList(null);
        // 判断 flag 如果为 true，将 99 置为 isActive
        for (ChengHallDictT brand : chengBrandDictTList) {
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHalltypeId(String.valueOf(brand.getUuid()));
            hallTypeVO.setHalltypeName(brand.getShowName());

            // flag = true，需要 99
            // flag = false，匹配到的内容为 active
            if (flag) {
                if (brand.getUuid() == 99) {
                    hallTypeVO.setActive(true);
                }
            } else {
                if (brand.getUuid() == hallType) {
                    hallTypeVO.setActive(true);
                } else {
                    hallTypeVO.setActive(false);
                }
            }

            hallTypeVOList.add(hallTypeVO);
        }

        return hallTypeVOList;
    }

    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {

        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();

        ChengCinemaT chengCinemaT = chengCinemaTMapper.selectById(cinemaId);
        if (chengCinemaT == null) {
            return cinemaInfoVO;
        }

        cinemaInfoVO.setImgUrl(chengCinemaT.getImgAddress());
        cinemaInfoVO.setCinemaPhone(chengCinemaT.getCinemaPhone());
        cinemaInfoVO.setCinemaName(chengCinemaT.getCinemaName());
        cinemaInfoVO.setCinemaId(String.valueOf(chengCinemaT.getUuid()));
        cinemaInfoVO.setCinemaAdress(chengCinemaT.getCinemaAddress());

        return cinemaInfoVO;
    }

    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId) {
        return chengFieldTMapper.getFilmInfos(cinemaId);
    }

    @Override
    public HallInfoVO getFilmFieldInfo(int fieldId) {
        return chengFieldTMapper.getHallInfo(fieldId);
    }

    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        return chengFieldTMapper.getFilmInfoById(fieldId);
    }
}
