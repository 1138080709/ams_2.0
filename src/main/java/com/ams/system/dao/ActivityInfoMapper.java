package com.ams.system.dao;

import com.ams.system.entity.ActivityInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityInfoMapper {

    /**
     * 查询指定活动
     */
    ActivityInfo selectByPrimaryKey(Integer activityId);

    /**
     * 编辑活动
     */
    int updateByPrimaryKey(ActivityInfo record);

    /**
     * 新增活动
     */
    int insert(ActivityInfo record);

    /**
     * 删除指定活动
     */
    int deleteByPrimaryKey(@Param("activityId") Integer activityId);

    /**
     * 根据条件查询活动
     */
    List<ActivityInfo> selectAllWithQuery(ActivityInfo activityInfoQuery);
}