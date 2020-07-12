package com.ams.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class File implements Serializable {
    @JsonProperty("id")
    private Integer fileId;

    private String groupName;

    private String remoteFileName;

    @JsonProperty("name")
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    private String remark;

    private String type;
    
    private Integer dirId;

    private Date createTime;

    private Date modifyTime;

    private String creator;

    private String updator;

    private static final long serialVersionUID = 1L;
    
    public File() {
        
    }

    public File(String fileName, String remoteFileName, @NotBlank(message = "文件名不能为空") String folderName, String type, Integer dirId, String creator) {
        this.groupName = fileName;
        this.remoteFileName = remoteFileName;
        this.fileName = folderName;
        this.type = type;
        this.dirId = dirId;
        this.creator = creator;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName == null ? null : remoteFileName.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getDirId() {
        return dirId;
    }

    public void setDirId(Integer dirId) {
        this.dirId = dirId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fileId=").append(fileId);
        sb.append(", groupName=").append(groupName);
        sb.append(", remoteFileName=").append(remoteFileName);
        sb.append(", fileName=").append(fileName);
        sb.append(", remark=").append(remark);
        sb.append(", type=").append(type);
        sb.append(", dirId=").append(dirId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", creator=").append(creator);
        sb.append(", updator=").append(updator);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}