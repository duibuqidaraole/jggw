package com.mt.government.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 发布者
     */
    private String publisher;

    /**
     * 发布时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态 0 关闭 1 发布显示
     */
    private Integer status;

    private String files;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

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
     * 获取公告标题
     *
     * @return title - 公告标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置公告标题
     *
     * @param title 公告标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取公告内容
     *
     * @return content - 公告内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置公告内容
     *
     * @param content 公告内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取发布者
     *
     * @return publisher - 发布者
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 设置发布者
     *
     * @param publisher 发布者
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 获取发布时间
     *
     * @return create_time - 发布时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发布时间
     *
     * @param createTime 发布时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取状态 0 关闭 1 发布显示
     *
     * @return status - 状态 0 关闭 1 发布显示
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0 关闭 1 发布显示
     *
     * @param status 状态 0 关闭 1 发布显示
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}