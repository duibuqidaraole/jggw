package com.mt.government.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 提交任务自动扣分配置
 */
@Data
public class DeductedConfig {
    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 每天扣多少分
     */
    @Column(name = "points_per_day")
    private Float pointsPerDay;

    /**
     * 最多扣多少分 0表示无限制
     */
    @Column(name = "max_deducted_points")
    private Float maxDeductedPoints;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
