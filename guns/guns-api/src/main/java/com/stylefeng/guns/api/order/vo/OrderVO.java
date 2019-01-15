package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/15 17:03
 */
@Data
public class OrderVO implements Serializable {

    private static final long serialVersionUID = -2862496625669886701L;

    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderTimestamp;
    private String orderStatus;
}
