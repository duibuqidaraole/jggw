package com.mt.government.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 807303140476042703L;
    /**
     * 用户id，登陆名
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户登陆密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 账号角色 0:工委 1:机构 2:部门
     */
    @Column(name = "user_role")
    private Integer userRole;

    /**
     * 账号创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 账号更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 用户状态 0:禁用 1:启用 2:删除
     */
    @Column(name = "user_status")
    private Integer userStatus;

    /**
     * 单位id
     */
    @Column(name = "org_id")
    private Integer orgId;

    /**
     * 单位名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 换届提醒
     */
    @Column(name = "quantitative_flag")
    private Integer quantitativeFlag;

    public Integer getQuantitativeFlag() {
        return quantitativeFlag;
    }

    public void setQuantitativeFlag(Integer quantitativeFlag) {
        this.quantitativeFlag = quantitativeFlag;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取用户id，登陆名
     *
     * @return user_id - 用户id，登陆名
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id，登陆名
     *
     * @param userId 用户id，登陆名
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户登陆密码
     *
     * @return password - 用户登陆密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户登陆密码
     *
     * @param password 用户登陆密码
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 获取账号角色 0:工委 1:机构 2:部门
     *
     * @return user_role - 账号角色 0:工委 1:机构 2:部门
     */
    public Integer getUserRole() {
        return userRole;
    }

    /**
     * 设置账号角色 0:工委 1:机构 2:部门
     *
     * @param userRole 账号角色 0:工委 1:机构 2:部门
     */
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    /**
     * 获取账号创建时间
     *
     * @return create_time - 账号创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置账号创建时间
     *
     * @param createTime 账号创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取用户状态 0:禁用 1:启用 2:删除
     *
     * @return user_status - 用户状态 0:禁用 1:启用 2:删除
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 设置用户状态 0:禁用 1:启用 2:删除
     *
     * @param userStatus 用户状态 0:禁用 1:启用 2:删除
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}