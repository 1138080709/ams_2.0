package com.ams.system.dao;

import com.ams.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    Role selectByPrimaryKey(Integer roleId);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
    
// ------------------------------------------------  //

    /**
     * 新增角色
     */
    int insert(Role record);

    /**
     * 更新角色
     */
    int updateByPrimaryKeySelective(Role role);
}