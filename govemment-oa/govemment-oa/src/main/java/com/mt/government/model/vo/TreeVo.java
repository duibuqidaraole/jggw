package com.mt.government.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 返回前端树形图结构
 *
 * @author g
 * @date 2019-11-27 16:03
 */
@Data
public class TreeVo {
    /**
     * 节点id
     */
    private Integer id;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 父节点id
     */
    private Integer pId;
    /**
     * 预留属性1
     */
    private Object param1;
    /**
     * 预留属性2
     */
    private Object param2;
    /**
     * 子节点
     */
    private List<TreeVo> children;
}
