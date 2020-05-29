package com.ams.system.dao;

import com.ams.system.entity.Operator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperatorMapper {

    /**
     * 获取全部操作权限
     */
    List<Operator> selectAll();

    /**
     * 新增操作权限
     */
    int insert(Operator record);

    /**
     * 更新操作权限
     */
    int updateByPrimaryKey(Operator record);

    /**
     * 删除指定操作权限
     */
    int deleteByPrimaryKey(@Param("operatorId") Integer operatorId);

    /**
     * 获取指定的操作
     */
    Operator selectByPrimaryKey(@Param("operatorId") Integer operatorId);

    /**
     * 删除指定菜单下的操作权限
     */
    void deleteByMenuId(@Param("menuId") Integer menuId);

    /**
     * 查询指定菜单下的操作权限
     */
    List<Operator> selectByMenuId(Integer menuId);
}