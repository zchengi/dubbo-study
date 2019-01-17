package com.alipay.demo.trade.model.hb;

/**
 * Created by liuyangkly on 15/9/28.
 */
public interface TradeInfo {
    // 获取交易状态
    HbStatus getStatus();

    // 获取交易时间
    double getTimeConsume();
}
