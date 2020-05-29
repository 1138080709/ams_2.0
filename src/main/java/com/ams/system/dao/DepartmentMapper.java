package com.ams.system.dao;

import com.ams.system.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {


    /**
     * 查询所有部门
     */
    List<Department> selectAll();

    /**
     * 编辑部门
     */
    int updateByPrimaryKey(Department record);

    /**
     * 新增部门
     */
    int insert(Department record);

    /**
     * 删除指定部门
     */
    int deleteByPrimaryKey(@Param("departmentId") Integer departmentId);

    /**
     * 查询部门信息
     */
    Department selectByPrimaryKey(@Param("departmentId") Integer departmentId);

    /**
     * 查询指定条件的部门信息
     */
    List<Department> selectAllWithQuery(Department departmentQuery);
}