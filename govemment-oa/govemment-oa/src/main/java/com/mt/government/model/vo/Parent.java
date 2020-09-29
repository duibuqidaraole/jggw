package com.mt.government.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 接收前端传过来的上级单位信息
 *
 * @author fuzhigang
 * @date 2019/10/12
 */
@Data
@AllArgsConstructor
public class Parent {
    private Integer parentId;
    private String parentName;
}
