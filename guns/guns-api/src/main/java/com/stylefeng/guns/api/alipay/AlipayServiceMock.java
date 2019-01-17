package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AlipayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AlipayResultVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务降级方法
 *
 * @author cheng
 *         2019/1/17 14:17
 */
@Slf4j
public class AlipayServiceMock implements AlipayServiceAPI {

    @Override
    public AlipayInfoVO getQRCode(String orderId) {
        return null;
    }

    @Override
    public AlipayResultVO getOrderStatus(String orderId) {

        AlipayResultVO alipayResultVO = new AlipayResultVO();
        alipayResultVO.setOrderId(orderId);
        alipayResultVO.setOrderStatus(0);
        alipayResultVO.setOrderMsg("支付失败");
        log.error("支付失败 mock error");
        return alipayResultVO;
    }
}
