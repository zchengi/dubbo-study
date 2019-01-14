package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cheng
 *         2019/1/13 22:41
 */
@Data
public class HallInfoVO implements Serializable {

    private static final long serialVersionUID = 482709851953246406L;

    private String hallFieldId;
    private String hallName;
    private String price;
    private String seatFile;
    /**
     * 已售座位必须关联订单才能查询
     */
    private String soldSeats;
}
