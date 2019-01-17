package com.cheng.dubbo;

import org.mengyun.tcctransaction.api.Compensable;

/**
 * @author cheng
 *         2019/1/17 20:34
 */
public interface ServiceAPI {

    @Compensable
    String sendMessage(String message);

    /**
     *
     * 背景：传入购票数量、传入购买座位、影厅编号
     * 业务：
     *      1. 判断传入的座位是否存在
     *      2. 查询过往订单、判断座位是否已售
     *      3. 新增订单
     * 逻辑：
     *      1. 新增一条订单
     *      2. 判断座位是否存在 & 是否已售
     *      3. 任意一条为假，则修改订单为无效状态
     *
     */

    /**
     * 座位是否存在
     */
    @Compensable
    boolean isTrueSeats(String seats);

    /**
     * 座位是否已售
     */
    @Compensable
    boolean isNotSold(String seats);

    /**
     * 保存订单
     */
    @Compensable
    String saveOrder(String fieldId, String seats, String seatsNum);
}
