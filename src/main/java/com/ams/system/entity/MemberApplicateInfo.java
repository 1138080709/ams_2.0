package com.ams.system.entity;

import java.io.Serializable;
import java.util.Date;

public class MemberApplicateInfo implements Serializable {
    private Integer applicateId;

    private String name;

    private String digits;

    private String college;

    private String major;

    private String belongClass;

    private String grade;

    private String phone;

    private String email;

    private String applicateSection;

    private String applicateJob;

    private Integer applicateFlag;

    private Date applicateTime;

    private static final long serialVersionUID = 1L;

    public Integer getApplicateId() {
        return applicateId;
    }

    public void setApplicateId(Integer applicateId) {
        this.applicateId = applicateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits == null ? null : digits.trim();
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getBelongClass() {
        return belongClass;
    }

    public void setBelongClass(String belongClass) {
        this.belongClass = belongClass == null ? null : belongClass.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getApplicateSection() {
        return applicateSection;
    }

    public void setApplicateSection(String applicateSection) {
        this.applicateSection = applicateSection == null ? null : applicateSection.trim();
    }

    public String getApplicateJob() {
        return applicateJob;
    }

    public void setApplicateJob(String applicateJob) {
        this.applicateJob = applicateJob == null ? null : applicateJob.trim();
    }

    public Integer getApplicateFlag() {
        return applicateFlag;
    }

    public void setApplicateFlag(Integer applicateFlag) {
        this.applicateFlag = applicateFlag;
    }

    public Date getApplicateTime() {
        return applicateTime;
    }

    public void setApplicateTime(Date applicateTime) {
        this.applicateTime = applicateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", applicateId=").append(applicateId);
        sb.append(", name=").append(name);
        sb.append(", digits=").append(digits);
        sb.append(", college=").append(college);
        sb.append(", major=").append(major);
        sb.append(", belongClass=").append(belongClass);
        sb.append(", grade=").append(grade);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", applicateSection=").append(applicateSection);
        sb.append(", applicateJob=").append(applicateJob);
        sb.append(", applicateFlag=").append(applicateFlag);
        sb.append(", applicateTime=").append(applicateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}