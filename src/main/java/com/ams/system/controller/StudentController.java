package com.ams.system.controller;

import com.ams.common.utils.ResultBean;
import com.ams.common.validator.Create;
import com.ams.common.validator.Update;
import com.ams.system.dao.StudentMapper;
import com.ams.system.entity.Student;
import com.ams.system.entity.User;
import com.ams.system.service.StudentService;
import com.ams.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/24 15:53
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Resource
    private StudentService studentService;
    

    @ApiOperation(value = "新增学生信息")
    @PostMapping
    @ResponseBody
    public ResultBean add(@Validated(Create.class) Student student) {
        return ResultBean.success(studentService.add(student));
    }
    
    @ApiOperation(value = "完善学生信息")
    @PostMapping("/fill")
    @ResponseBody
    public ResultBean fill(@Validated(Create.class) Student student) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        studentService.fillStudentInfo(user,student);
        return ResultBean.success();
    }
    
    @ApiOperation(value="编辑学生信息")
    @PutMapping
    @ResponseBody
    public ResultBean edit(@Validated(Update.class) Student student) {
        studentService.edit(student);
        return ResultBean.success();
    }
    
    @ApiOperation(value = "删除学生信息")
    @DeleteMapping("/{studentId}")
    @ResponseBody
    public ResultBean del(@PathVariable("studentId") int studentId) {
        studentService.delete(studentId);
        return ResultBean.success();
    }

    @ApiOperation(value = "查询学生信息", notes = "获取当前用户的学生信息")
    @GetMapping
    @ResponseBody
    public ResultBean getStudent() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Student student = studentService.selectStudentByUser(user);
        return ResultBean.success(student);
    }

    @ApiOperation(value = "查询学生信息", notes = "获取指定studentId的学生信息")
    @GetMapping("/{studentId}")
    @ResponseBody
    public ResultBean getStudent(@PathVariable("studentId") int studentId) {
        Student student = studentService.selectStudentByStudentId(studentId);
        return ResultBean.success(student);
    }
    
    

}
