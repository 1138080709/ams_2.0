package com.ams.system.dao;

import com.ams.system.entity.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    /**
     * 根据主键删除文件信息
     */
    int deleteByPrimaryKey(Integer fileId);

    /**
     * 新增文件信息
     */
    int insert(File record);

    /**
     * 根据主键查询文件信息
     */
    File selectByPrimaryKey(Integer fileId);

    /**
     * 根据查询条件获取所有文件信息
     */
    List<File> selectAllWithQuery(File record);

    /**
     * 编辑文件信息
     */
    int updateByPrimaryKey(File record);
    
}