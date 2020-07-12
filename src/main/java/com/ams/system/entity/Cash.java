package com.ams.system.entity;

import com.ams.common.validator.Create;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Cash implements Serializable {
    
    @JsonProperty("id")
    private Integer cashId;
    
    @NotNull(message = "信息类型不能为空",groups = Create.class)
    private Integer infoType;

    @NotNull(message = "金额不能为空",groups = Create.class)
    private Integer money;

    private String remark;

    private Integer proposerId;

    private Date applicateTime;

    private Integer auditorId;

    private Integer auditFlag;

    private Integer executeFlag;
    
    private Student proposer;
    
    private Student auditor;

    private static final long serialVersionUID = 1L;

    public Integer getCashId() {
        return cashId;
    }

    public void setCashId(Integer cashId) {
        this.cashId = cashId;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getProposerId() {
        return proposerId;
    }

    public void setProposerId(Integer proposerId) {
        this.proposerId = proposerId;
    }

    public Date getApplicateTime() {
        return applicateTime;
    }

    public void setApplicateTime(Date applicateTime) {
        this.applicateTime = applicateTime;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public Integer getExecuteFlag() {
        return executeFlag;
    }

    public void setExecuteFlag(Integer executeFlag) {
        this.executeFlag = executeFlag;
    }

    public Student getProposer() {
        return proposer;
    }

    public void setProposer(Student proposer) {
        this.proposer = proposer;
    }

    public Student getAuditor() {
        return auditor;
    }

    public void setAuditor(Student auditor) {
        this.auditor = auditor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cashId=").append(cashId);
        sb.append(", infoType=").append(infoType);
        sb.append(", money=").append(money);
        sb.append(", remark=").append(remark);
        sb.append(", proposerId=").append(proposerId);
        sb.append(", applicateTime=").append(applicateTime);
        sb.append(", auditorId=").append(auditorId);
        sb.append(", auditFlag=").append(auditFlag);
        sb.append(", executeFlag=").append(executeFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}