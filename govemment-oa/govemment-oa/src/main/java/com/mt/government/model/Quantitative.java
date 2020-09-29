package com.mt.government.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Quantitative {
    /**
     * 自增ID
     */
    @Id
    private Integer id;

    /**
     * 党组织名称
     */
    @Column(name = "party_name")
    private String partyName;

    /**
     * 党书记
     */
    @Column(name = "party_secretary")
    private String partySecretary;

    /**
     * 本次时间
     */
    @Column(name = "start_day")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    private Date startDay;

    /**
     * 下次时间
     */
    @Column(name = "end_day")
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    private Date endDay;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 副书记
     */
    @Column(name = "deputy_secretary")
    private String deputySecretary;

    /**
     * 委员
     */
    @Column(name = "committee")
    private String committee;
}