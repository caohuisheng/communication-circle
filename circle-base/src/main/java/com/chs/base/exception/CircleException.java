package com.chs.base.exception;

/**
 * Author: chs
 * Description: 自定义异常类
 * CreateTime: 2025-07-06
 */
public class CircleException extends RuntimeException{
    private String errMessage;

    public CircleException() {
        super();
    }

    public CircleException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    /**
     * 抛出自定义异常信息
     * @param commonError 通用异常
     */
    public static void cast(CommonError commonError){
        throw new CircleException(commonError.getErrMessage());
    }

    public static void cast(String errMessage){
        throw new CircleException(errMessage);
    }

}
