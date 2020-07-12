package com.ams.system.controller;

import com.ams.common.utils.PageResultBean;
import com.ams.common.utils.ResultBean;
import com.ams.common.validator.Create;
import com.ams.common.validator.Update;
import com.ams.system.entity.Department;
import com.ams.system.entity.Student;
import com.ams.system.service.DepartmentService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/28 1:41
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    
    @Resource
    private DepartmentService departmentService;
    
    @GetMapping("/index")
    public String index() {
        return "department/department-list";
    }
    
    @GetMapping("/add")
    public String add() {
        return "department/department-add";
    }
    
    @ApiOperation("新增部门")
    @PostMapping
    @ResponseBody
    public ResultBean add(@Validated(Create.class) Department department) {
        return ResultBean.success(departmentService.add(department));
    }

    @GetMapping("/edit/{departmentId}")
    public String edit(Model model, @PathVariable("departmentId") Integer departmentId) {
        Department department = departmentService.getDepartmentInfo(departmentId);
        model.addAttribute("department",department);
        return "department/department-add";
    }

    @ApiOperation("编辑部门")
    @PutMapping
    @ResponseBody
    public ResultBean edit(@Validated(Update.class) Department department) {
        departmentService.edit(department);
        return ResultBean.success();
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/{departmentId}")
    @ResponseBody
    public ResultBean del(@PathVariable("departmentId") Integer departmentId) {
        departmentService.delete(departmentId);
        return ResultBean.success();
    }
    
    @ApiOperation("查询部门信息")
    @GetMapping("/{departmentId}")
    @ResponseBody
    public ResultBean getDepartmentInfo(@PathVariable("departmentId") Integer departmentId) {
        return ResultBean.success(departmentService.getDepartmentInfo(departmentId));
    }


    @GetMapping("/member/{departmentId}")
    public String getDepartmentMember(Model model, @PathVariable("departmentId") Integer departmentId) {
        Department department = departmentService.getDepartmentInfo(departmentId);
        model.addAttribute("department",department);
        return "department/department-member";
    }
    
    @ApiOperation("查询部门成员")
    @GetMapping("/member/{departmentId}/list")
    @ResponseBody
    public ResultBean getDepartmentMember(@PathVariable("departmentId") Integer departmentId) {
        return ResultBean.success(departmentService.getDepartmentMember(departmentId));
    }
    
    @ApiOperation(value = "获取部门列表",notes = "分页")
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Department> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "limit", defaultValue = "10") int limit,
                                              Department departmentQuery) {
        List<Department> departments = departmentService.selectAllWithQuery(page, limit, departmentQuery);
        PageInfo<Department> departmentPageInfo = new PageInfo<>(departments); 
        return new PageResultBean<>(departmentPageInfo.getTotal(), departmentPageInfo.getList());
    }
    
}
