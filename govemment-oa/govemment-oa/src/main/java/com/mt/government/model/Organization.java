package com.mt.government.model;

import com.mt.government.model.vo.Parent;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Organization {
    @Id
    @Column(name = "org_id")
    private Integer orgId;

    /**
     * 单位名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 单位电话
     */
    @Column(name = "org_telephone")
    private String orgTelephone;

    /**
     * 单位地址
     */
    @Column(name = "org_address")
    private String orgAddress;

    /**
     * 上级单位
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 是否为上级单位 0:不是 1:是
     */
    @Column(name = "is_parent")
    private Integer isParent;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private Parent parent;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Date getCreateTime() {
        return createTime;
    }

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
     * @return org_id
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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
     * 获取单位电话
     *
     * @return org_telephone - 单位电话
     */
    public String getOrgTelephone() {
        return orgTelephone;
    }

    /**
     * 设置单位电话
     *
     * @param orgTelephone 单位电话
     */
    public void setOrgTelephone(String orgTelephone) {
        this.orgTelephone = orgTelephone;
    }

    /**
     * 获取单位地址
     *
     * @return org_address - 单位地址
     */
    public String getOrgAddress() {
        return orgAddress;
    }

    /**
     * 设置单位地址
     *
     * @param orgAddress 单位地址
     */
    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }
}