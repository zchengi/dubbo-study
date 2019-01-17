package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AlipayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AlipayResultVO;

/**
 * 业务降级方法
 *
 * @author cheng
 *         2019/1/17 14:17
 */
public class AlipayServiceMock implements AlipayServiceAPI{

    @Override
    public AlipayInfoVO getQRCode(String orderId) {
        return null;
    }

    @Override
    public AlipayResultVO getOrderStatus(String orderId) {

        AlipayResultVO alipayResultVO = new AlipayResultVO();
        alipayResultVO.setOrderId(orderId);
        alipayResultVO.setOrderStatus(0);
        alipayResultVO.setOrderMsg("未支付成功");

        return alipayResultVO;
    }
}
