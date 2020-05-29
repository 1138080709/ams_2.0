package com.ams.system.entity;

import java.io.Serializable;

public class ActivityParticipatorInfo implements Serializable {
    private Integer participatorId;

    private String participatorName;

    private String participatorDigits;

    private String participatorCollege;

    private String participatorClass;

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