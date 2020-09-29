package com.mt.government.enums;

import lombok.Getter;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 14:55
 */
@Getter
public enum ComStatusEnum {
    CLOSE(0, "任务关闭"),
    OPEN(1, "开启任务");

    private Integer code;
    private String msg;

    ComStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
