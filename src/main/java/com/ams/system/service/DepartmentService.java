package com.ams.system.service;

import com.ams.system.dao.DepartmentMapper;
import com.ams.system.dao.StudentDepartmentMapper;
import com.ams.system.entity.Department;
import com.ams.system.entity.Student;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/28 1:42
 */
@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentDao;

    @Resource
    private StudentDepartmentMapper studentDepartmentDao;


    public Integer add(Department department) {
        return departmentDao.insert(department);
    }

    @Transactional
    public void delete(Integer departmentId) {
        departmentDao.deleteByPrimaryKey(departmentId);
        studentDepartmentDao.deleteByDepartmentId(departmentId);
    }

    public Department getDepartmentInfo(Integer departmentId) {
        return departmentDao.selectByPrimaryKey(departmentId);
    }

    public void edit(Department department) {
        departmentDao.updateByPrimaryKey(department);
    }

    public List<Student> getDepartmentMember(Integer departmentId) {
        return studentDepartmentDao.queryStudentInfoByDepartmentId(departmentId);
    }

    public List<Department> selectAll() {
        return departmentDao.selectAll();
    }

    public List<Department> selectAllWithQuery(int page, int limit, Department departmentQuery) {
        PageHelper.startPage(page, limit);
        return departmentDao.selectAllWithQuery(departmentQuery);
    }
}
