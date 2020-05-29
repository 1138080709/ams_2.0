package com.ams.system.dao;

import com.ams.system.entity.ActivityParticipatorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityParticipatorInfoMapper {
    int deleteByPrimaryKey(Integer participatorId);

    int insert(ActivityParticipatorInfo record);

    ActivityParticipatorInfo selectByPrimaryKey(Integer participatorId);

    List<ActivityParticipatorInfo> selectAll();

    int updateByPrimaryKey(ActivityParticipatorInfo record);

    /**
     * 删除指定活动的参与者信息
     */
    void deleteByActivityId(@Param("activityId") Integer activityId);
}