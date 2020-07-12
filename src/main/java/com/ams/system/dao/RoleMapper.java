package com.ams.system.dao;

import com.ams.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    /**
     * 根据主键查询角色信息
     */
    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKey(Role record);
    

    /**
     * 新增角色
     */
    int insert(Role record);

    /**
     * 更新角色
     */
    int updateByPrimaryKeySelective(Role role);

    /**
     * 查询所有角色
     */
    List<Role> selectAll();

    /**
     * 根据条件查询角色
     */
    List<Role> selectAllWithQuery(Role roleQuery);
}