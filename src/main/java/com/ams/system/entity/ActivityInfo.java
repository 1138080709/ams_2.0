package com.ams.system.entity;

import com.ams.common.validator.Create;
import com.ams.common.validator.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ActivityInfo implements Serializable {
    @JsonProperty("id")
    @NotNull(message = "活动id不能为空", groups = Update.class)
    private Integer activityId;
    
    @NotBlank(message = "活动主题不能为空", groups = Create.class)
    private String activityTheme;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "活动开始时间不能为空")
    private Date startTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "活动结束时间不能为空")
    private Date overTime;

    private String principalName;

    private Integer activityFlag;

    private String detailInfo;
    
    private String imgSrc;

    private static final long serialVersionUID = 1L;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme == null ? null : activityTheme.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName == null ? null : principalName.trim();
    }

    public Integer getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(Integer activityFlag) {
        this.activityFlag = activityFlag;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo == null ? null : detailInfo.trim();
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", activityId=").append(activityId);
        sb.append(", activityTheme=").append(activityTheme);
        sb.append(", startTime=").append(startTime);
        sb.append(", overTime=").append(overTime);
        sb.append(", principalName=").append(principalName);
        sb.append(", activityFlag=").append(activityFlag);
        sb.append(", detailInfo=").append(detailInfo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}