package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/17 14:13
 */
@Data
public class AlipayInfoVO implements Serializable {

    private static final long serialVersionUID = -4597523763670495557L;

    private String orderId;
    private String QRCodeAddress;
}
