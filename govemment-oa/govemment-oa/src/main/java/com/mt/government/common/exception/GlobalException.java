package com.mt.government.common.exception;


/**
 * 自定义全局异常
 */
public class GlobalException extends RuntimeException {

    public GlobalException(String message, int code) {
        super(message);
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
