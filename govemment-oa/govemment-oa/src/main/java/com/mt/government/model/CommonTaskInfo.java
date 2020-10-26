package com.mt.government.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table(name = "common_task_info")
public class CommonTaskInfo {
    /**
     * 任务id
     */
    @Id
    @Column(name = "common_task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commonTaskId;

    /**
     * 任务分类
     */
    @Column(name = "task_no")
    private String taskNo;

    /**
     * 任务创建时间
     */
    @Column(name = "common_task_create_time")
    private Date commonTaskCreateTime;

    /**
     * 任务创建人
     */
    @Column(name = "common_task_publisher")
    private String commonTaskPublisher;

    /**
     * 任务开始日期
     */
    @Column(name = "start_day")
    @JsonFormat(locale = "zh" , timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDay;

    /**
     * 任务结束日期
     */
    @Column(name = "end_day")
    @JsonFormat(locale = "zh" , timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDay;

    /**
     * 任务接收人
     */
    private String receivers;

    /**
     * 任务状态 0:关闭 1:开启 2:删除
     */
    @Column(name = "common_task_status")
    private Integer commonTaskStatus;

    /**
     * 任务标题
     */
    @Column(name = "common_task_title")
    private String commonTaskTitle;

    /**
     * 任务附件地址
     */
    @Column(name = "files")
    private String files;

    /**
     * 任务备注
     */
    private String comment;

    /**
     * 任务内容
     */
    @Column(name = "common_task_content")
    private String commonTaskContent;

    private User user;

    /**
     * 是否加入考核
     * 0 未加入
     * 1 加入
     */
    @Column(name = "isExamine")
    private boolean isExamine;

}