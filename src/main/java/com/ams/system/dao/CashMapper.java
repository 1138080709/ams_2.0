package com.ams.system.dao;

import com.ams.system.entity.Cash;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CashMapper {
    int deleteByPrimaryKey(Integer cashId);

    int insert(Cash record);

    Cash selectByPrimaryKey(Integer cashId);

    List<Cash> selectAll();

    int updateByPrimaryKey(Cash record);
}