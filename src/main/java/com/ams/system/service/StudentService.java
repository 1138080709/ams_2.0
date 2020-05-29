package com.ams.system.service;

import com.ams.common.exception.DuplicateDigitsException;
import com.ams.common.exception.DuplicateFillStudentInfoException;
import com.ams.common.exception.DuplicateNameException;
import com.ams.common.exception.NoFillStudentInfoException;
import com.ams.system.dao.StudentDepartmentMapper;
import com.ams.system.dao.StudentMapper;
import com.ams.system.dao.UserMapper;
import com.ams.system.entity.Student;
import com.ams.system.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/26 12:51
 */
@Service
public class StudentService {

    @Resource
    private StudentMapper studentDao;

    @Resource
    private UserMapper userDao;
    
    @Resource
    private StudentDepartmentMapper studentDepartmentDao;

    public Student selectStudentByStudentId(Integer studentId) {
        return studentDao.selectByPrimaryKey(studentId);
    }

    public Student selectStudentByUser(User user) {
        if (user.getStudentId() == null)
            throw new NoFillStudentInfoException();
        return studentDao.selectByPrimaryKey(user.getStudentId());
    }

    @Transactional
    public Integer add(Student student) {
        checkDigitsExistOnCreate(student.getDigits());
        studentDao.insertSelective(student);
        return student.getStudentId();
    }

    @Transactional
    public void delete(int studentId) {
        studentDao.deleteByPrimaryKey(studentId);
        userDao.setNullWithStudentId(studentId);
        studentDepartmentDao.deleteByStudentId(studentId);
    }

    @Transactional
    public void fillStudentInfo(User user, Student student) {
        if (user.getStudentId() != null)
            throw new DuplicateFillStudentInfoException();
        checkDigitsExistOnCreate(student.getDigits());
        studentDao.insertSelective(student);
        user.setStudentId(student.getStudentId());
        userDao.updateByPrimaryKeySelective(user);
    }

    @Transactional
    public void edit(Student student) {
        checkDigitsExistOnUpdate(student.getDigits(), student.getStudentId());
        studentDao.updateByPrimaryKeySelective(student);

    }

    public void checkDigitsExistOnCreate(String digits) {
        if (studentDao.countByDigits(digits) > 0) {
            throw new DuplicateDigitsException();
        }
    }

    public void checkDigitsExistOnUpdate(String digits, int studentId) {
        if (studentDao.countByDigitsNotIncludeStudentId(digits, studentId) > 0) {
            throw new DuplicateDigitsException();
        }
    }
}
