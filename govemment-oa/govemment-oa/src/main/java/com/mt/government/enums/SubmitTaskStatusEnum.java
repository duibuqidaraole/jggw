package com.mt.government.enums;

import lombok.Getter;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 17:24
 */
@Getter
public enum SubmitTaskStatusEnum {
    APPROVAL(1, "已审批"),
    UNAPPROVAL(0, "未审批");

    private Integer code;
    private String msg;

    SubmitTaskStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
