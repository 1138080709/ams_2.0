package com.ams.system.entity;

import java.io.Serializable;
import java.util.Date;

public class CashFlowInfo implements Serializable {
    private Integer cashFlowId;

    private Integer infoType;

    private Integer money;

    private String remark;

    private Integer proposerId;

    private Date applicateTime;

    private Integer auditorId;

    private Integer auditFlag;

    private Integer executeFlag;

    private static final long serialVersionUID = 1L;

    public Integer getCashFlowId() {
        return cashFlowId;
    }

    public void setCashFlowId(Integer cashFlowId) {
        this.cashFlowId = cashFlowId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cashFlowId=").append(cashFlowId);
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