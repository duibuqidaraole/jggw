package com.mt.government.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "task_category")
public class TaskCategory {
    /**
     * 主键id
     */
    @Id
    private Integer id;

    /**
     * 任务分类名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String remark;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务分类名称
     *
     * @return name - 任务分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置任务分类名称
     *
     * @param name 任务分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取描述
     *
     * @return describe - 描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置描述
     *
     * @param remark 描述
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}