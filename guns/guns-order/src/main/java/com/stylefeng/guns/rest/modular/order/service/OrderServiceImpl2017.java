package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.api.cinema.vo.OrderQueryVO;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.UUIDUtil;
import com.stylefeng.guns.rest.common.persistence.dao.ChengOrder2017TMapper;
import com.stylefeng.guns.rest.common.persistence.model.ChengOrder2017T;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 *         2019/1/15 17:08
 */
@Slf4j
@Component
@Service(interfaceClass = OrderServiceAPI.class, group = "order2017")
public class OrderServiceImpl2017 implements OrderServiceAPI {

    @Autowired
    private ChengOrder2017TMapper chengOrder2017TMapper;

    @Reference(interfaceClass = CinemaServiceAPI.class, check = false)
    private CinemaServiceAPI cinemaServiceAPI;

    @Autowired
    private FTPUtil ftpUtil;

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {

        // 根据 FieldId 找到对应的座位位置图
        String seatPath = chengOrder2017TMapper.getSeatsByFieldId(fieldId);

        // 读取位置图，判断 seats 是否售出
        String fileStrByAddress = ftpUtil.getFileStrByAddress(seatPath);

        // 将 fileStrByAddress 转换为 JSON对象
        JSONObject jsonObject = JSONObject.parseObject(fileStrByAddress);
        // seats=1,2,3   ids="1,3,4,5,6,7,88"
        String ids = jsonObject.get("ids").toString();

        // 每一次匹配上的，都给 isTrueLength+1
        String[] seatArray = seats.split(",");
        String[] idArray = ids.split(",");

        int isTrueLength = 0;
        for (String id : idArray) {
            for (String seat : seatArray) {
                if (seat.equalsIgnoreCase(id)) {
                    isTrueLength++;
                }
            }
        }

        // 如果匹配上的数量与已售座位数一致，则全部匹配上
        return seatArray.length == isTrueLength;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {

        EntityWrapper<ChengOrder2017T> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("field_id", fieldId);

        List<ChengOrder2017T> list = chengOrder2017TMapper.selectList(entityWrapper);
        String[] seatArray = seats.split(",");

        // 有一个匹配到，直接返回失败
        for (ChengOrder2017T chengOrder2017T : list) {
            String[] ids = chengOrder2017T.getSeatsIds().split(",");
            for (String id : ids) {
                for (String seat : seatArray) {
                    if (id.equalsIgnoreCase(seat)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {

        // 编号
        String uuid = UUIDUtil.generateUuid();

        // 影片信息
        FilmInfoVO filmInfoVO = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
        Integer filmId = Integer.parseInt(filmInfoVO.getFilmId());

        // 影院信息
        OrderQueryVO orderQueryVO = cinemaServiceAPI.getOrderNeeds(fieldId);
        Integer cinemaId = Integer.valueOf(orderQueryVO.getCinemaId());
        double filmPrice = Double.parseDouble(orderQueryVO.getFilmPrice());

        // 订单总金额
        int solds = soldSeats.split(",").length;
        double totalPrice = getTotalPrice(solds, filmPrice);

        ChengOrder2017T chengOrder2017T = new ChengOrder2017T();
        chengOrder2017T.setUuid(uuid);
        chengOrder2017T.setSeatsName(seatsName);
        chengOrder2017T.setSeatsIds(soldSeats);
        chengOrder2017T.setOrderUser(userId);
        chengOrder2017T.setOrderPrice(totalPrice);
        chengOrder2017T.setFilmPrice(filmPrice);
        chengOrder2017T.setFilmId(filmId);
        chengOrder2017T.setFieldId(fieldId);
        chengOrder2017T.setCinemaId(cinemaId);

        Integer insert = chengOrder2017TMapper.insert(chengOrder2017T);
        if (insert > 0) {
            // 返回查询结果
            OrderVO orderVO = chengOrder2017TMapper.getOrderInfoById(uuid);
            if (orderVO == null || orderVO.getOrderId() == null) {
                log.error("订单信息查询失败，订单编号为: {}", uuid);
            } else {
                return orderVO;
            }
        } else {
            // 数据库插入失败
            log.error("订单插入失败");
        }

        return null;
    }

    @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {

        Page<OrderVO> result = new Page<>();
        if (userId == null) {
            log.error("订单查询失败，用户编号为空");
            return null;
        } else {

            List<OrderVO> ordersByUserId = chengOrder2017TMapper.getOrdersByUserId(userId, page);
            if (ordersByUserId == null || ordersByUserId.size() == 0) {
                result.setTotal(0);
                result.setRecords(new ArrayList<>());
            } else {
                // 获取订单总数
                EntityWrapper<ChengOrder2017T> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("order_user", userId);
                Integer count = chengOrder2017TMapper.selectCount(entityWrapper);
                result.setTotal(count);
                result.setRecords(ordersByUserId);
            }

            return result;
        }
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        // 已售座位信息
        // 1  1, 2, 3, 4
        // 2  5, 6, 7

        if (fieldId == null) {
            log.error("查询已售座位出错，未输入场次编号");
            return "";
        } else {
            return chengOrder2017TMapper.getSoldSeatsByFieldId(fieldId);
        }
    }

    @Override
    public OrderVO getOrderInfoById(String orderId) {
        return chengOrder2017TMapper.getOrderInfoById(orderId);
    }

    @Override
    public boolean paySuccess(String orderId) {

        ChengOrder2017T chengOrderT = new ChengOrder2017T();
        chengOrderT.setUuid(orderId);
        chengOrderT.setOrderStatus(1);

        Integer integer = chengOrder2017TMapper.updateById(chengOrderT);
        return integer >= 1;
    }

    @Override
    public boolean payFail(String orderId) {

        ChengOrder2017T chengOrderT = new ChengOrder2017T();
        chengOrderT.setUuid(orderId);
        chengOrderT.setOrderStatus(2);

        Integer integer = chengOrder2017TMapper.updateById(chengOrderT);
        return integer >= 1;
    }
    private double getTotalPrice(int solds, double filmPrice) {

        BigDecimal soldsDeci = new BigDecimal(solds);
        BigDecimal filmPriceDeci = new BigDecimal(filmPrice);

        // 乘积
        BigDecimal sum = soldsDeci.multiply(filmPriceDeci);
        // 四舍五入
        BigDecimal res = sum.setScale(2, RoundingMode.HALF_UP);

        return res.doubleValue();
    }
}
