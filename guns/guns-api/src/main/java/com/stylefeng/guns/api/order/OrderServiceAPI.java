package com.stylefeng.guns.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.OrderVO;

/**
 * @author cheng
 *         2019/1/15 17:01
 */
public interface OrderServiceAPI {

    /**
     * 验证是否售出票
     */
    boolean isTrueSeats(String fieldId, String seats);

    /**
     * 判断订单中的座位是否已售出
     */
    boolean isNotSoldSeats(String fieldId, String seats);

    /**
     * 创建订单信息
     */
    OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId);

    /**
     * 获取当前用户登录信息
     */
    Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page);

    /**
     * 根据 FieldId 获取所有已售座位的编号
     */
    String getSoldSeatsByFieldId(Integer fieldId);

    // ---------- 支付模块 ----------

    /**
     * 根据订单编号获取订单信息
     */
    OrderVO getOrderInfoById(String orderId);

    /**
     * 支付成功
     */
    boolean paySuccess(String orderId);

    /**
     * 支付失败
     */
    boolean payFail(String orderId);
}
