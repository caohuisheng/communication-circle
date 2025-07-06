package com.chs.base.model;

import lombok.Data;
import lombok.ToString;

/**
 * Author: chs
 * Description: 通用响应结果
 * CreateTime: 2025-06-29
 */
@Data
@ToString
public class CommonResult<T> {
    /**
     * 响应编码,0为正常,-1错误
     */
    private int code;

    /**
     * 响应提示信息
     */
    private String msg;

    /**
     * 响应内容
     */
    private T data;

    public CommonResult() {
        this(0, "success");
    }

    public CommonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误信息的封装
     * @param msg 消息
     * @param <T> 结果泛型
     * @return
     */
    public static <T> CommonResult<T> fail(String msg) {
        CommonResult<T> response = new CommonResult<T>();
        response.setCode(-1);
        response.setMsg(msg);
        return response;
    }

    public static <T> CommonResult<T> fail(T result, String msg) {
        CommonResult<T> response = new CommonResult<T>();
        response.setCode(-1);
        response.setData(result);
        response.setMsg(msg);
        return response;
    }

    /**
     * 添加正常响应数据
     * @return Rest服务封装相应数据
     */
    public static <T> CommonResult<T> success(T result) {
        CommonResult<T> response = new CommonResult<T>();
        response.setData(result);
        return response;
    }

    /**
     * 添加正常响应消息
     * @return Rest服务封装相应数据
     */
    public static <T> CommonResult<T> success(String msg) {
        CommonResult<T> response = new CommonResult<T>();
        response.setMsg(msg);
        return response;
    }

    public static <T> CommonResult<T> success(T result, String msg) {
        CommonResult<T> response = new CommonResult<T>();
        response.setData(result);
        response.setMsg(msg);
        return response;
    }

    /**
     * 添加正常响应数据（不包含响应内容）
     * @return CommonResult Rest服务封装相应数据
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>();
    }

    public Boolean isSuccess() {
        return this.code == 0;
    }
}
