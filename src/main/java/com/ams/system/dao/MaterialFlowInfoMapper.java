package com.ams.system.dao;

import com.ams.system.entity.MaterialFlowInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialFlowInfoMapper {
    int deleteByPrimaryKey(Integer materialFlowId);

    int insert(MaterialFlowInfo record);

    MaterialFlowInfo selectByPrimaryKey(Integer materialFlowId);

    List<MaterialFlowInfo> selectAll();

    int updateByPrimaryKey(MaterialFlowInfo record);
}