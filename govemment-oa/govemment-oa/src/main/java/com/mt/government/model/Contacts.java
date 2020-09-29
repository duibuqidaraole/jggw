package com.mt.government.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 联系人实体类
 */
@Data
public class Contacts {

    @Id
    private Integer id;

    /**
     * 联系人姓名
     */
    @Column(name = "linkman")
    private String linkman;

    /**
     * 性别 0：女 1：男 2：未知
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 座机
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 手机
     */
    @Column(name = "position")
    private String position;

    /**
     * 联系地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 所属单位
     */
    @Column(name = "user_id")
    private String userId;

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
