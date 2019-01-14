package com.stylefeng.guns.rest.modular.vo;

import lombok.Data;

/**
 * Controller 返回 VO
 *
 * @author cheng
 *         2019/1/3 23:46
 */
@Data
public class ResponseVO<T> {

    /**
     * 返回状态:
     * 0: 成功
     * 1: 失败
     * 999: 系统异常
     */
    private int status;

    private String msg;

    private T data;

    /**
     * 图片路径前缀
     */
    private String imgPre;

    /**
     * 分页
     */
    private int nowPage;
    private int totalPage;

    private ResponseVO() {
    }

    public static <T> ResponseVO success(T t) {

        ResponseVO<T> responseVO = new ResponseVO<>();

        responseVO.setStatus(0);
        responseVO.setData(t);

        return responseVO;
    }

    public static <T> ResponseVO success(String imgPre, T t) {

        ResponseVO<T> responseVO = new ResponseVO<>();

        responseVO.setStatus(0);
        responseVO.setData(t);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public static <T> ResponseVO success(String imgPre, T t, int nowPage, int totalPage) {

        ResponseVO<T> responseVO = new ResponseVO<>();

        responseVO.setStatus(0);
        responseVO.setData(t);
        responseVO.setImgPre(imgPre);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);

        return responseVO;
    }

    public static ResponseVO success(String message) {

        ResponseVO responseVO = new ResponseVO<>();

        responseVO.setMsg(message);

        return responseVO;
    }

    public static ResponseVO serviceFail(String message) {

        ResponseVO responseVO = new ResponseVO<>();

        responseVO.setStatus(1);
        responseVO.setMsg(message);

        return responseVO;
    }

    public static ResponseVO appFail(String message) {

        ResponseVO responseVO = new ResponseVO<>();

        responseVO.setStatus(999);
        responseVO.setMsg(message);

        return responseVO;
    }
}
