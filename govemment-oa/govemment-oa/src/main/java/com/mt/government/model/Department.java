package com.mt.government.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class Department {
    /**
     * 部门id
     */
    @Id
    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 部门编号
     */
    @Column(name = "dept_no")
    private String deptNo;

    /**
     * 上级部门
     */
    @Column(name = "superior_dept")
    private Integer superiorDept;

    /**
     * 部门电话
     */
    @Column(name = "dept_telephone")
    private String deptTelephone;

    /**
     * 部门领导
     */
    @Column(name = "dept_leader_id")
    private String deptLeaderId;

    /**
     * 所属单位
     */
    @Column(name = "org_id")
    private Integer orgId;

    /**
     * 获取部门id
     *
     * @return dept_id - 部门id
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 设置部门id
     *
     * @param deptId 部门id
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取部门名称
     *
     * @return dept_name - 部门名称
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置部门名称
     *
     * @param deptName 部门名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取部门编号
     *
     * @return dept_no - 部门编号
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * 设置部门编号
     *
     * @param deptNo 部门编号
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * 获取上级部门
     *
     * @return superior_dept - 上级部门
     */
    public Integer getSuperiorDept() {
        return superiorDept;
    }

    /**
     * 设置上级部门
     *
     * @param superiorDept 上级部门
     */
    public void setSuperiorDept(Integer superiorDept) {
        this.superiorDept = superiorDept;
    }

    /**
     * 获取部门电话
     *
     * @return dept_telephone - 部门电话
     */
    public String getDeptTelephone() {
        return deptTelephone;
    }

    /**
     * 设置部门电话
     *
     * @param deptTelephone 部门电话
     */
    public void setDeptTelephone(String deptTelephone) {
        this.deptTelephone = deptTelephone;
    }

    /**
     * 获取部门领导
     *
     * @return dept_leader_id - 部门领导
     */
    public String getDeptLeaderId() {
        return deptLeaderId;
    }

    /**
     * 设置部门领导
     *
     * @param deptLeaderId 部门领导
     */
    public void setDeptLeaderId(String deptLeaderId) {
        this.deptLeaderId = deptLeaderId;
    }

    /**
     * 获取所属单位
     *
     * @return org_id - 所属单位
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * 设置所属单位
     *
     * @param orgId 所属单位
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}