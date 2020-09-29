package com.mt.government.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户提交任务列表视图
 *
 * @author fuzhigang
 * @date 2019-10-17
 */
@Data
public class MySubmitListVo {
    /**
     * 提交任务id
     */
    private Integer id;
    /**
     * 任务id
     */
    private Integer taskId;
    /**
     * 提交任务标题
     */
    private String title;
    /**
     * 任务标题
     */
    private String taskTitle;
    /**
     * 任务发布单位
     */
    private String orgName;
    /**
     * 任务发布时间
     */
    private Date publishTime;
    /**
     * 任务提交时间
     */
    private Date submitTime;
}
