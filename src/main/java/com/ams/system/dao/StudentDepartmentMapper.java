package com.ams.system.dao;

import com.ams.system.entity.Student;
import com.ams.system.entity.StudentDepartment;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentDepartmentMapper {
    int deleteByPrimaryKey(@Param("studentId") Integer studentId, @Param("departmentId") Integer departmentId);

    int insert(StudentDepartment record);

    List<StudentDepartment> selectAll();
    
//  ------------------------------------------------  //    

    /**
     * 根据studentId删除关联关系
     */
    int deleteByStudentId(@Param("studentId") Integer studentId);

    /**
     * 根据departmentId删除关联关系
     */
    int deleteByDepartmentId(@Param("departmentId") Integer departmentId);

    /**
     * 查询指定部门的学生信息
     */
    List<Student> queryStudentInfoByDepartmentId(@Param("departmentId") Integer departmentId);
}