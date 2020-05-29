package com.ams.system.dao;

import com.ams.system.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    int deleteByPrimaryKey(Integer studentId);

    int insert(Student record);
    
    List<Student> selectAll();

    int updateByPrimaryKey(Student record);

//  -------------------------------------------  //

    /**
     * 根据studentId获取学生信息
     */
    Student selectByPrimaryKey(@Param("studentId") Integer studentId);

    /**
     * 统计学号出现的次数
     */
    Integer countByDigits(@Param("digits") String digits);

    /**
     * 添加学生信息
     */
    Integer insertSelective(Student record);

    /**
     * 统计学号出现的次数 不包含studentId
     */
    Integer countByDigitsNotIncludeStudentId(@Param("digits") String digits, @Param("studentId") int studentId);

    /**
     * 更新学生信息
     */
    Integer updateByPrimaryKeySelective(Student student);
}