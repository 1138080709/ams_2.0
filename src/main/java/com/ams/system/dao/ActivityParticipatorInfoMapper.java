package com.ams.system.dao;

import com.ams.system.entity.ActivityParticipatorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityParticipatorInfoMapper {
    /**
     * 根据id删除指定参与者信息
     */
    int deleteByPrimaryKey(Integer participatorId);

    /**
     * 添加指定活动的参与者信息
     */
    int insert(ActivityParticipatorInfo record);

    ActivityParticipatorInfo selectByPrimaryKey(Integer participatorId);

    List<ActivityParticipatorInfo> selectAll();

    /**
     * 修改指定活动的参与者信息
     */
    int updateByPrimaryKey(ActivityParticipatorInfo record);

    /**
     * 删除指定活动的参与者信息
     */
    void deleteByActivityId(@Param("activityId") Integer activityId);

    /**
     * 获取指定活动的所有参与者信息
     */
    List<ActivityParticipatorInfo> selectAllByActivityId(Integer activityId);
}