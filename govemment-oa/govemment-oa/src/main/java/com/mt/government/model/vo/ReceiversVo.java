package com.mt.government.model.vo;

import com.mt.government.model.Organization;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 发布任务选择接收对象
 */

@Getter
@Setter
@ToString
public class ReceiversVo extends Organization {
    private String userId;
}
