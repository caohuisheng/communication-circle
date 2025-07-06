package com.chs.base.exception;

import com.chs.base.model.ErrorResult;
import com.sun.deploy.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-06
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CircleException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult customException(CircleException e){
        log.error("【系统异常】{}",e.getErrMessage(),e);
        return new ErrorResult(e.getErrMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult methodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<String> msgList = new ArrayList<>();
        //得到所有的参数异常并遍历，添加到列表中
        bindingResult.getFieldErrors().stream().forEach(error -> {
            msgList.add(error.getDefaultMessage());
        });
        String errMessage = StringUtils.join(msgList,",");
        log.error("【系统异常】{}", errMessage);
        return new ErrorResult(errMessage);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exception(Exception e) {
        log.error("【系统异常】{}",e.getMessage(),e);
        if(e.getMessage().equals("不允许访问")){
            return new ErrorResult("您没有权限操作此功能");
        }
        return new ErrorResult(CommonError.UNKNOWN_ERROR.getErrMessage());
    }
}
