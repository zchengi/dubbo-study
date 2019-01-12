package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.film.vo.ActorVO;
import com.stylefeng.guns.rest.common.persistence.model.ChengActorT;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author cheng
 * @since 2019-01-11
 */
@Repository
public interface ChengActorTMapper extends BaseMapper<ChengActorT> {

    List<ActorVO> getActors(@Param("filmId") String filmId);
}
