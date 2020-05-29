package com.ams.system.dao;

import com.ams.system.entity.RoleMenu;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMenuMapper {
    int deleteByPrimaryKey(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int insert(RoleMenu record);

    List<RoleMenu> selectAll();

//  --------------------------------------------------  //

    /**
     * 清空角色对应的菜单
     */    
    void deleteRoleMenuByRoleId(int roleId);

    /**
     * 清空菜单对应的角色
     */
    void deleteRoleMenuByMenuId(int MenuId);

    /**
     * 插入多条 角色-菜单 关联关系
     */
    void insertRoleMenus(@Param("roleId") int roleId, @Param("menuIds") Integer[] menuIds);

    /**
     * 获取职位对应的角色
     */
    Integer[] getMenuByRoleId(@Param("roleId") Integer roleId);
}