package com.mt.government.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Form {
    /**
     * 自增id
     */
    @Id
    private Integer id;

    /**
     * 创建时间
     */
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 表单内容
     */
    private String data;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return add_time - 创建时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置创建时间
     *
     * @param addTime 创建时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取表单内容
     *
     * @return data - 表单内容
     */
    public String getData() {
        return data;
    }

    /**
     * 设置表单内容
     *
     * @param data 表单内容
     */
    public void setData(String data) {
        this.data = data;
    }
}