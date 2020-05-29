package com.ams.system.entity;

import java.io.Serializable;

public class MaterialFlowInfo implements Serializable {
    private Integer materialFlowId;

    private Integer materialId;

    private Integer infoType;

    private Integer number;

    private String purpose;

    private String date;

    private String organizationName;

    private String principalName;

    private String principalPhone;

    private Integer auditorId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getMaterialFlowId() {
        return materialFlowId;
    }

    public void setMaterialFlowId(Integer materialFlowId) {
        this.materialFlowId = materialFlowId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName == null ? null : principalName.trim();
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone == null ? null : principalPhone.trim();
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", materialFlowId=").append(materialFlowId);
        sb.append(", materialId=").append(materialId);
        sb.append(", infoType=").append(infoType);
        sb.append(", number=").append(number);
        sb.append(", purpose=").append(purpose);
        sb.append(", date=").append(date);
        sb.append(", organizationName=").append(organizationName);
        sb.append(", principalName=").append(principalName);
        sb.append(", principalPhone=").append(principalPhone);
        sb.append(", auditorId=").append(auditorId);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}