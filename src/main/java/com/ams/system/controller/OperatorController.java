package com.ams.system.controller;

import com.ams.common.annotation.RefreshFilterChain;
import com.ams.common.utils.PageResultBean;
import com.ams.common.utils.ResultBean;
import com.ams.system.entity.Operator;
import com.ams.system.service.OperatorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/24 15:55
 */
@Controller
@RequestMapping("operator")
public class OperatorController {
    
    @Resource
    private OperatorService operatorService;
    
    @ApiOperation("新增操作")
    @RefreshFilterChain
    @PostMapping
    @ResponseBody
    public ResultBean add(Operator operator) {
        return ResultBean.success(operatorService.add(operator));
    }
    
    @ApiOperation("编辑操作")
    @RefreshFilterChain
    @PutMapping
    @ResponseBody
    public ResultBean edit(Operator operator) {
        operatorService.edit(operator);
        return ResultBean.success();
    }
    
    @ApiOperation("删除操作")
    @RefreshFilterChain
    @DeleteMapping("/{operatorId}")
    @ResponseBody
    public ResultBean del(@PathVariable("operatorId") Integer operatorId) {
        operatorService.delete(operatorId);
        return ResultBean.success();
    }
    
    @ApiOperation("获取指定的操作权限")
    @GetMapping("/{operatorId}")
    @ResponseBody
    public ResultBean getInfo(@PathVariable("operatorId") Integer operatorId) {
        return ResultBean.success(operatorService.selectOperatorByOperatorId(operatorId));
    }
    
    @ApiOperation(value = "获取指定菜单的操作列表")
    @GetMapping("/list")
    @ResponseBody
    public ResultBean getList(@RequestParam(required = false) Integer menuId) {
        return ResultBean.success(operatorService.selectByMenuId(menuId));
    }
    
    
}
