package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.TokenBucket;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 *         2019/1/14 17:25
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference(interfaceClass = OrderServiceAPI.class,
            check = false,
            group = "order2018")
    private OrderServiceAPI orderServiceAPI;

    @Reference(interfaceClass = OrderServiceAPI.class,
            check = false,
            group = "order2017")
    private OrderServiceAPI orderServiceAPI2017;

    /**
     * 购票
     */
    @PostMapping("buyTickets")
    public ResponseVO buyTickets(Integer fieldId, String soldSeats, String seatsName) {

        try {
            if (TokenBucket.getToken()) {
                // 验证是否售出票
                boolean isTrue = orderServiceAPI.isTrueSeats(String.valueOf(fieldId), soldSeats);

                // 判断订单中的座位是否已售出
                boolean isNotSold = orderServiceAPI.isNotSoldSeats(String.valueOf(fieldId), soldSeats);

                // 判断 isTrue isNotSold 是否为 true
                if (isTrue && isNotSold) {
                    // 创建订单信息
                    String userId = CurrentUser.getCurrentUser();
                    if (userId == null || userId.trim().length() == 0) {
                        return ResponseVO.serviceFail("用户未登录");
                    }

                    OrderVO orderVO = orderServiceAPI.saveOrderInfo(fieldId, soldSeats, seatsName, Integer.parseInt(userId));
                    if (orderVO == null) {
                        log.error("购票失败");
                        return ResponseVO.serviceFail("购票业务异常");
                    } else {
                        return ResponseVO.success(orderVO);
                    }
                } else {
                    return ResponseVO.serviceFail("订单中的座位编号有问题");
                }
            } else {
                return ResponseVO.serviceFail("购票人数过多，请稍后再试");
            }
        } catch (Exception e) {
            log.error("购票异常", e);
            return ResponseVO.serviceFail("购票业务异常");
        }
    }

    @PostMapping("getOrderInfo")
    public ResponseVO getOrderInfo(@RequestParam(required = false, defaultValue = "1") Integer nowPage,
                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {

        // 获取登录信息
        String userId = CurrentUser.getCurrentUser();

        // 获取当前用户订单
        Page<OrderVO> page = new Page<>(nowPage, pageSize);
        if (userId != null && userId.trim().length() > 0) {

            Page<OrderVO> result = orderServiceAPI.getOrderByUserId(Integer.valueOf(userId), page);
            Page<OrderVO> result2017 = orderServiceAPI2017.getOrderByUserId(Integer.valueOf(userId), page);

            // 合并结果
            int totalPages = (int) (result.getPages() + result2017.getPages());
            // 2017 + 2018 订单总数
            List<OrderVO> orderVOList = new ArrayList<>();
            orderVOList.addAll(result.getRecords());
            orderVOList.addAll(result2017.getRecords());

            return ResponseVO.success("", orderVOList, nowPage, totalPages);
        } else {
            return ResponseVO.serviceFail("用户未登录");
        }
    }
}
