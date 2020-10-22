package com.mt.government.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author administered
 * @date 2020-03-18 09:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "due2")
public class Due2 implements Serializable {
    private static final long serialVersionUID = -4061871218011824304L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;
    /**
     * 序号
     */
    private String no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 岗位
     */
    private String position;
    /**
     * 职务工资
     */
    @Column(name = "job_salary")
    private String jobSalary;
    /**
     * 技术工资
     */
    @Column(name = "level_salary")
    private String levelSalary;
    /**
     * 绩效
     */
    private String bonus;
    /**
     * 津贴
     */
    private String allowance;
    /**
     * 月工资总结
     */
    @Column(name = "month_total")
    private String monthTotal;
    /**
     * 医保
     */
    @Column(name = "medical_insurance")
    private String medicalInsurance;
    /**
     * 公积金
     */
    @Column(name = "provident_fund")
    private String providentFund;
    /**
     * 税费
     */
    private String tax;
    /**
     * 社保
     */
    @Column(name = "social_insurance")
    private String socialInsurance;
    /**
     * 其他
     */
    @Column(name = "other_deduct")
    private String otherDeduct;
    /**
     * 缴纳基数
     */
    @Column(name = "pay_base")
    private String payBase;
    /**
     * 在职
     */
    @Column(name = "in_service")
    private String inService;
    /**
     * 缴纳比例
     */
    @Column(name = "pay_percent")
    private String payPercent;
    /**
     * 每月党费
     */
    @Column(name = "monthly_dues")
    private String monthlyDues;
    /**
     * 备注
     */
    private String remark;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

    private String reason;

    private Integer point;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getLevelSalary() {
        return levelSalary;
    }

    public void setLevelSalary(String levelSalary) {
        this.levelSalary = levelSalary;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(String monthTotal) {
        this.monthTotal = monthTotal;
    }

    public String getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(String medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public String getProvidentFund() {
        return providentFund;
    }

    public void setProvidentFund(String providentFund) {
        this.providentFund = providentFund;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSocialInsurance() {
        return socialInsurance;
    }

    public void setSocialInsurance(String socialInsurance) {
        this.socialInsurance = socialInsurance;
    }

    public String getOtherDeduct() {
        return otherDeduct;
    }

    public void setOtherDeduct(String otherDeduct) {
        this.otherDeduct = otherDeduct;
    }

    public String getPayBase() {
        return payBase;
    }

    public void setPayBase(String payBase) {
        this.payBase = payBase;
    }

    public String getInService() {
        return inService;
    }

    public void setInService(String inService) {
        this.inService = inService;
    }

    public String getPayPercent() {
        return payPercent;
    }

    public void setPayPercent(String payPercent) {
        this.payPercent = payPercent;
    }

    public String getMonthlyDues() {
        return monthlyDues;
    }

    public void setMonthlyDues(String monthlyDues) {
        this.monthlyDues = monthlyDues;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Due2(String userId, String no, String name, String position, String jobSalary, String levelSalary, String bonus, String allowance, String monthTotal, String medicalInsurance, String providentFund, String tax, String socialInsurance, String otherDeduct, String payBase, String inService, String payPercent, String monthlyDues, String remark, Date createTime, Date updateTime) {
        this.userId = userId;
        this.no = no;
        this.name = name;
        this.position = position;
        this.jobSalary = jobSalary;
        this.levelSalary = levelSalary;
        this.bonus = bonus;
        this.allowance = allowance;
        this.monthTotal = monthTotal;
        this.medicalInsurance = medicalInsurance;
        this.providentFund = providentFund;
        this.tax = tax;
        this.socialInsurance = socialInsurance;
        this.otherDeduct = otherDeduct;
        this.payBase = payBase;
        this.inService = inService;
        this.payPercent = payPercent;
        this.monthlyDues = monthlyDues;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Due2{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", jobSalary='" + jobSalary + '\'' +
                ", levelSalary='" + levelSalary + '\'' +
                ", bonus='" + bonus + '\'' +
                ", allowance='" + allowance + '\'' +
                ", monthTotal='" + monthTotal + '\'' +
                ", medicalInsurance='" + medicalInsurance + '\'' +
                ", providentFund='" + providentFund + '\'' +
                ", tax='" + tax + '\'' +
                ", socialInsurance='" + socialInsurance + '\'' +
                ", otherDeduct='" + otherDeduct + '\'' +
                ", payBase='" + payBase + '\'' +
                ", inService='" + inService + '\'' +
                ", payPercent='" + payPercent + '\'' +
                ", monthlyDues='" + monthlyDues + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
