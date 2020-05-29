package com.ams.system.dao;

import com.ams.system.entity.MemberApplicateInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberApplicateInfoMapper {
    int deleteByPrimaryKey(Integer applicateId);

    int insert(MemberApplicateInfo record);

    MemberApplicateInfo selectByPrimaryKey(Integer applicateId);

    List<MemberApplicateInfo> selectAll();

    int updateByPrimaryKey(MemberApplicateInfo record);
}