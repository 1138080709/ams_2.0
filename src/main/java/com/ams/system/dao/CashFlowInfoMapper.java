package com.ams.system.dao;

import com.ams.system.entity.CashFlowInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CashFlowInfoMapper {
    int deleteByPrimaryKey(Integer cashFlowId);

    int insert(CashFlowInfo record);

    CashFlowInfo selectByPrimaryKey(Integer cashFlowId);

    List<CashFlowInfo> selectAll();

    int updateByPrimaryKey(CashFlowInfo record);
}