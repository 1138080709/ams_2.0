package com.ams.system.dao;

import com.ams.system.entity.RoleOperator;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleOperatorMapper {
    int deleteByPrimaryKey(@Param("roleId") Integer roleId, @Param("operatorId") Integer operatorId);

    int insert(RoleOperator record);

    List<RoleOperator> selectAll();
    
//  -------------------------------------------  //

    /**
     * 清空角色对应的操作
     */
    void deleteRoleOperatorByRoleId(@Param("roleId") int roleId);

    /**
     * 清空操作对应的角色
     */
    void deleteRoleOperatorByOperatorId(@Param("operatorId") int operatorId);

    /**
     * 插入多条 角色-操作权限 关联关系
     */
    void insertRoleOperators(@Param("roleId") int roleId, @Param("operatorIds") Integer[] operatorIds);

    /**
     * 获取角色对应的操作权限
     */
    Integer[] getOperatorByRoleId(@Param("roleId") Integer roleId);
}