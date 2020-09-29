package com.mt.government.enums;

import lombok.Getter;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 11:41
 * 任务分类状态
 */
@Getter
public enum TaskCateStatusEnum {
    CLOSE(0, "关闭"),
    OPEN(1, "开启");

    private Integer code;
    private String msg;

    TaskCateStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
