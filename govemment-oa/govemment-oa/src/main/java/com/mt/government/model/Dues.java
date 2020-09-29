package com.mt.government.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 党费信息
 *
 * @author g
 * @date 2019-11-12 11:26
 */
@Data
@ToString
public class Dues {
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
     * 表格类型
     */
    private Integer type;

    /**
     * 文件地址
     */
    private String files;

    /**
     * 用户id
     */
    @Column
    private String userId;
}
