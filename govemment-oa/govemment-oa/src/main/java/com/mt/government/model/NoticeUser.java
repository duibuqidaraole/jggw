package com.mt.government.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 通知-用户关联记录
 *
 * @author fuzhigang
 * @date 2019-10-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeUser {
    /**
     * 自增id
     */
    @Id
    private Long id;

    /**
     * 通知id
     */
    @Column(name = "notice_id")
    private Integer noticeId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 状态 0：未读 1：已读
     */
    private Integer status;

    /**
     * 操作时间
     */
    @Column(name = "oper_time")
    private Date operTime;
}
