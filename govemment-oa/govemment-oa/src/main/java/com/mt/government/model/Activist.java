package com.mt.government.model;

import java.util.Date;
import javax.persistence.*;

public class Activist {
    /**
     * 自增id
     */
    @Id
    private Integer id;

    /**
     * 单位名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 姓名
     */
    @Column(name = "activist_name")
    private String activistName;

    /**
     * 提交入党申请时间
     */
    @Column(name = "sqlo_time")
    private Date sqloTime;

    /**
     * 转为入党积极分之时间
     */
    @Column(name = "apa_time")
    private Date apaTime;

    /**
     * 转为发展对象时间
     */
    @Column(name = "do_time")
    private Date doTime;

    /**
     * 转为预备党员时间
     */
    @Column(name = "ppm_time")
    private Date ppmTime;

    /**
     * 转为正式党员时间
     */
    @Column(name = "fmp_time")
    private Date fmpTime;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取单位名称
     *
     * @return org_name - 单位名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置单位名称
     *
     * @param orgName 单位名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取姓名
     *
     * @return activist_name - 姓名
     */
    public String getActivistName() {
        return activistName;
    }

    /**
     * 设置姓名
     *
     * @param activistName 姓名
     */
    public void setActivistName(String activistName) {
        this.activistName = activistName;
    }

    /**
     * 获取提交入党申请时间
     *
     * @return sqlo_time - 提交入党申请时间
     */
    public Date getSqloTime() {
        return sqloTime;
    }

    /**
     * 设置提交入党申请时间
     *
     * @param sqloTime 提交入党申请时间
     */
    public void setSqloTime(Date sqloTime) {
        this.sqloTime = sqloTime;
    }

    /**
     * 获取转为入党积极分之时间
     *
     * @return apa_time - 转为入党积极分之时间
     */
    public Date getApaTime() {
        return apaTime;
    }

    /**
     * 设置转为入党积极分之时间
     *
     * @param apaTime 转为入党积极分之时间
     */
    public void setApaTime(Date apaTime) {
        this.apaTime = apaTime;
    }

    /**
     * 获取转为发展对象时间
     *
     * @return do_time - 转为发展对象时间
     */
    public Date getDoTime() {
        return doTime;
    }

    /**
     * 设置转为发展对象时间
     *
     * @param doTime 转为发展对象时间
     */
    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    /**
     * 获取转为预备党员时间
     *
     * @return ppm_time - 转为预备党员时间
     */
    public Date getPpmTime() {
        return ppmTime;
    }

    /**
     * 设置转为预备党员时间
     *
     * @param ppmTime 转为预备党员时间
     */
    public void setPpmTime(Date ppmTime) {
        this.ppmTime = ppmTime;
    }

    /**
     * 获取转为正式党员时间
     *
     * @return fmp_time - 转为正式党员时间
     */
    public Date getFmpTime() {
        return fmpTime;
    }

    /**
     * 设置转为正式党员时间
     *
     * @param fmpTime 转为正式党员时间
     */
    public void setFmpTime(Date fmpTime) {
        this.fmpTime = fmpTime;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}