package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/17 14:14
 */
@Data
public class AlipayResultVO implements Serializable {

    private static final long serialVersionUID = -4854323205997856606L;

    private String orderId;
    private Integer orderStatus;
    private String orderMsg;
}
