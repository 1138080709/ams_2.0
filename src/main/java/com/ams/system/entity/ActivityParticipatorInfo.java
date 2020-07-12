package com.ams.system.entity;

import com.ams.common.validator.Update;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ActivityParticipatorInfo implements Serializable {
    @JsonProperty("id")
    @NotBlank(message = "参与者id不能为空", groups = Update.class)
    private Integer participatorId;

    @JsonProperty("name")
    @NotBlank(message = "参与者姓名不能为空")
    private String participatorName;

    @JsonProperty("digits")
    @NotBlank(message = "参与者学号不能为空")
    private String participatorDigits;

    @JsonProperty("college")
    private String participatorCollege;

    @JsonProperty("class")
    private String participatorClass;

    @NotBlank(message = "参与的活动Id不能为空")
    private String activityId;

    private static final long serialVersionUID = 1L;

    public Integer getParticipatorId() {
        return participatorId;
    }

    public void setParticipatorId(Integer participatorId) {
        this.participatorId = participatorId;
    }

    public String getParticipatorName() {
        return participatorName;
    }

    public void setParticipatorName(String participatorName) {
        this.participatorName = participatorName == null ? null : participatorName.trim();
    }

    public String getParticipatorDigits() {
        return participatorDigits;
    }

    public void setParticipatorDigits(String participatorDigits) {
        this.participatorDigits = participatorDigits == null ? null : participatorDigits.trim();
    }

    public String getParticipatorCollege() {
        return participatorCollege;
    }

    public void setParticipatorCollege(String participatorCollege) {
        this.participatorCollege = participatorCollege == null ? null : participatorCollege.trim();
    }

    public String getParticipatorClass() {
        return participatorClass;
    }

    public void setParticipatorClass(String participatorClass) {
        this.participatorClass = participatorClass == null ? null : participatorClass.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", participatorId=").append(participatorId);
        sb.append(", participatorName=").append(participatorName);
        sb.append(", participatorDigits=").append(participatorDigits);
        sb.append(", participatorCollege=").append(participatorCollege);
        sb.append(", participatorClass=").append(participatorClass);
        sb.append(", activityId=").append(activityId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}