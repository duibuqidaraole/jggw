package com.mt.government.enums;

import lombok.Getter;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/17 14:34
 */
@Getter
public enum NoticeStatusEnum {
    SHOW(1, "显示通知"),
    NOSHOW(0, "不显示通知");

    private Integer code;
    private String msg;

    NoticeStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
