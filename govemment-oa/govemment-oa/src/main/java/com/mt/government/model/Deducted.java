package com.mt.government.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 扣分信息
 *
 * @author g
 * @date 2019-11-11 9:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Deducted {
    /**
     * 自增id
     */
    @Id
    private Long id;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 扣分用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 扣分值
     */
    private Float point;

    /**
     * 扣分原因
     */
    private String reason;

    /**
     * 扣分任务
     */
    @Column(name = "task_id")
    private Integer taskId;

    public Deducted(Long id, String createBy, Date createTime, String updateBy, Date updateTime, String userId, Float point, String reason, Integer taskId) {
        this.id = id;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.userId = userId;
        this.point = point;
        this.reason = reason;
        this.taskId = taskId;
    }

    @Transient
    private Integer type;
}
