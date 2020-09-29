package com.mt.government.common.exception;

/**
 * 用户已存在异常
 *
 * @author fuzhigang
 * @date 2019/10/10
 */
public class UserException extends GlobalException {

    public UserException(String message, int code) {
        super(message, code);
    }
}
