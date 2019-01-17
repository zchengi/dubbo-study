package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AlipayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AlipayResultVO;

/**
 * @author cheng
 *         2019/1/17 14:13
 */
public interface AlipayServiceAPI {

    /**
     * 获取二维码地址
     */
    AlipayInfoVO getQRCode(String orderId);

    /**
     * 获取订单支付状态
     */
    AlipayResultVO getOrderStatus(String orderId);
}
