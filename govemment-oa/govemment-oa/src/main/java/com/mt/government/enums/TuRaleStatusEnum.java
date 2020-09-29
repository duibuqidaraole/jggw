package com.mt.government.enums;

import lombok.Getter;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 15:17
 */
@Getter
public enum TuRaleStatusEnum {

    UNREAD(0, "未读"),
    SUBMIT(1, "已提交");


    private Integer code;
    private String msg;

    TuRaleStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
