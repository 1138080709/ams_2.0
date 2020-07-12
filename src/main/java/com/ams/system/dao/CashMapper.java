package com.ams.system.dao;

import com.ams.system.entity.Cash;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CashMapper {
    /**
     * 删除主键删除资金流动信息
     */
    int deleteByPrimaryKey(@Param("cashId") Integer cashId);

    /**
     * 新增资金流动信息
     */
    int insert(Cash record);

    /**
     * 根据主键获取资金流动信息
     */
    Cash selectByPrimaryKey(@Param("cashId") Integer cashId);

    List<Cash> selectAll();

    /**
     * 编辑资金流动信息
     */
    int updateByPrimaryKey(Cash record);

    /**
     * 根据条件查询资金流动信息
     */
    List<Cash> selectAllWithQuery(Cash cashQuery);

    /**
     * 根据条件查询待审核资金流动信息
     */
    List<Cash> selectCashAuditsWithQuery(Cash cashQuery);

    /**
     * 根据条件查询待执行资金流动信息
     */
    List<Cash> selectCashExecutesWithQuery(Cash cashQuery);
}