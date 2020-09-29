package com.mt.government.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "task_user_relationship")
public class TaskUserRelationship {
    /**
     * 用户-任务关联表记录id
     */
    @Id
    private Integer id;

    /**
     * 关联任务id
     */
    @Column(name = "task_id")
    private Integer taskId;

    /**
     * 接受任务用户id
     */
    @Column(name = "receiver_id")
    private String receiverId;

    /**
     * 任务接受状态 0:未接受 1:已接受 2:已提交
     */
    @Column(name = "receiver_status")
    private Integer receiverStatus;

    /**
     * 接受时间
     */
    @Column(name = "receiver_time")
    private Date receiverTime;

    private User user;

    private CommonTaskInfo commonTaskInfo;

}