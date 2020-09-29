package com.mt.government.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 发布任务详情视图对象
 *
 * @author fuzhigang
 */
@Getter
@Setter
@ToString
public class CommonTaskDetailVo {
    /**
     * 关联记录id
     */
    private Integer id;
    /**
     * 任务id
     */
    private Integer taskId;
    /**
     * 接受用户id
     */
    private String receiverId;
    /**
     * 接收单位名称
     */
    private String receiverName;
    /**
     * 接受状态 0:未提交 1:已提交
     */
    private Integer receiverStatus;
    /**
     * 接收时间
     */
    private Date receiverTime;
    /**
     * 提交任务id
     */
    private Integer submitId;
    /**
     * 是否超时
     */
    private Integer isOvertime;
    /**
     * 是否评分
     */
    private Integer deducted;

    private String userId;
}
