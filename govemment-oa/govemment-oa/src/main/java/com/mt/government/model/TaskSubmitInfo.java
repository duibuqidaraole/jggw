package com.mt.government.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "task_submit_info")
public class TaskSubmitInfo implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "submit_id")
    private Integer submitId;

    /**
     * 对应的任务id
     */
    @Column(name = "common_task_id")
    private Integer commonTaskId;

    /**
     * 提交任务标题
     */
    @Column(name = "submit_title")
    private String submitTitle;

    /**
     * 提交处理状态 0:未读 1:已读 2:已处理
     */
    private Integer status;

    /**
     * 任务提交时间
     */
    @Column(name = "submit_time")
    private Date submitTime;

    /**
     * 任务提交人
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 提交任务内容
     */
    @Column(name = "submit_content")
    private String submitContent;

    /**
     * 提交任务附件
     */
    @Column(name = "files")
    private String files;

    /**
     * 提交任务是否过期 0:否 1:是
     */
    @Column(name = "is_overtime")
    private Integer isOvertime;

    /**
     * 是否评分
     */
    private Integer deducted;

    /**
     * 获取主键id
     *
     * @return submit_id - 主键id
     */
    public Integer getSubmitId() {
        return submitId;
    }

    /**
     * 设置主键id
     *
     * @param submitId 主键id
     */
    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    /**
     * 获取对应的任务id
     *
     * @return common_task_id - 对应的任务id
     */
    public Integer getCommonTaskId() {
        return commonTaskId;
    }

    /**
     * 设置对应的任务id
     *
     * @param commonTaskId 对应的任务id
     */
    public void setCommonTaskId(Integer commonTaskId) {
        this.commonTaskId = commonTaskId;
    }

    /**
     * 获取提交任务标题
     *
     * @return submit_title - 提交任务标题
     */
    public String getSubmitTitle() {
        return submitTitle;
    }

    /**
     * 设置提交任务标题
     *
     * @param submitTitle 提交任务标题
     */
    public void setSubmitTitle(String submitTitle) {
        this.submitTitle = submitTitle;
    }

    /**
     * 获取提交处理状态 0:未读 1:已读 2:已处理
     *
     * @return status - 提交处理状态 0:未读 1:已读 2:已处理
     */
    public Integer getStatus() {
        return status;
    }


    /**
     * 设置提交处理状态 0:未读 1:已读 2:已处理
     *
     * @param status 提交处理状态 0:未读 1:已读 2:已处理
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取任务提交时间
     *
     * @return submit_time - 任务提交时间
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 设置任务提交时间
     *
     * @param submitTime 任务提交时间
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 获取任务提交人
     *
     * @return user_id - 任务提交人
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置任务提交人
     *
     * @param userId 任务提交人
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取提交任务内容
     *
     * @return submit_content - 提交任务内容
     */
    public String getSubmitContent() {
        return submitContent;
    }

    /**
     * 设置提交任务内容
     *
     * @param submitContent 提交任务内容
     */
    public void setSubmitContent(String submitContent) {
        this.submitContent = submitContent;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(Integer isOvertime) {
        this.isOvertime = isOvertime;
    }

    private User user;

    private CommonTaskInfo commonTaskInfo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommonTaskInfo getCommonTaskInfo() {
        return commonTaskInfo;
    }

    public void setCommonTaskInfo(CommonTaskInfo commonTaskInfo) {
        this.commonTaskInfo = commonTaskInfo;
    }

    public Integer getDeducted() {
        return deducted;
    }

    public void setDeducted(Integer deducted) {
        this.deducted = deducted;
    }
}