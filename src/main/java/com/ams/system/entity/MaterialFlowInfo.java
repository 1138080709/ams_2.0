package com.ams.system.entity;

import com.ams.common.validator.Create;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.sql.rowset.CachedRowSet;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class MaterialFlowInfo implements Serializable {
    @JsonProperty("id")
    private Integer materialFlowId;

    @NotNull(message = "物资不能为空", groups = Create.class)
    private Integer materialId;

    @NotNull(message = "信息类型不能为空", groups = Create.class)
    private Integer infoType;

    @NotNull(message = "物资数量不能为空", groups = Create.class)
    private Integer number;

    private String purpose;

    private Date date;

    private String organizationName;

    private String principalName;

    private String principalPhone;

    private Integer auditorId;

    private Integer auditFlag;

    private String remark;

    private Student auditor;

    private Material material;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Student getAuditor() {
        return auditor;
    }

    public void setAuditor(Student auditor) {
        this.auditor = auditor;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
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