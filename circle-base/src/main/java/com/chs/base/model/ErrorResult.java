package com.chs.base.model;

import java.io.Serializable;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-06
 */
public class ErrorResult implements Serializable {

    private String errMessage;

    public ErrorResult(String errMessage){
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

}
