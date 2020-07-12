package com.ams.system.dao;

import com.ams.system.entity.UserRole;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int insert(UserRole record);

    List<UserRole> selectAll();

//  --------------------------------------------------  //

    /**
     * 清空用户所拥有的角色
     */
    void deleteUserRoleByUserId(@Param("userId") Integer userId);

    /**
     * 插入多条 用户-角色 关联关系
     */
    void insertList(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    /**
     * 清空角色对应的用户
     */
    void deleteUserRoleByRoleId(@Param("roleId") Integer roleId);

    /**
     * 查询角色下的所有用户Id
     */
    List<Integer> selectUserIdByRoleId(@Param("roleId") int roleId);

    /**
     * 查询用户的所有角色
     */
    Integer selectRoleIdByUserId(@Param("userId") Integer userId);
}