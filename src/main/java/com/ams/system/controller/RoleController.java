package com.ams.system.controller;

import com.ams.common.utils.PageResultBean;
import com.ams.common.utils.ResultBean;
import com.ams.system.entity.Role;
import com.ams.system.service.RoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/24 15:55
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    
    @Resource
    private RoleService roleService;
    
    @GetMapping("/index")
    public String index() {
        return "role/role-list";
    }
    
    @ApiOperation("查询角色列表")
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Role> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        Role roleQuery) {
        List<Role> roles = roleService.selectAllWithQuery(page,limit,roleQuery);
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);
        return new PageResultBean<>(rolePageInfo.getTotal(),rolePageInfo.getList());
    }
    
    @GetMapping
    public String add() {
        return "role/role-add";
    }
    
    @ApiOperation("新增角色")
    @PostMapping
    @ResponseBody
    public ResultBean add(Role role) {
        return ResultBean.success(roleService.add(role));
    }
    
    @GetMapping("/{roleId}")
    public String edit(@PathVariable("roleId")Integer roleId, Model model) {
        Role role = roleService.selectOneById(roleId);
        model.addAttribute("role",role);
        return "role/role-add";
    }
    
    @ApiOperation("编辑角色")
    @PutMapping
    @ResponseBody
    public ResultBean edit(Role role) {
        roleService.edit(role);
        return ResultBean.success();
    }
    
    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    @ResponseBody
    public ResultBean del(@PathVariable("roleId") int roleId) {
        roleService.delete(roleId);
        return ResultBean.success();
    }

    @ApiOperation("为角色授予菜单")
    @PostMapping("/{roleId}/grant/menu")
    @ResponseBody
    public ResultBean grantMenu(@PathVariable("roleId") int roleId, @RequestParam(value = "menu[]", required = false) Integer[] menuIds) {
        roleService.grantMenu(roleId,menuIds);
        return ResultBean.success();
    }

    @ApiOperation("为角色授予操作权限")
    @PostMapping("/{roleId}/grant/operator")
    @ResponseBody
    public ResultBean grantOperator(@PathVariable("roleId") int roleId, @RequestParam(value = "operator[]", required = false) Integer[] operatorIds) {
        roleService.grantOperator(roleId,operatorIds);
        return ResultBean.success();
    } 
    
    @ApiOperation("获取角色拥有的菜单")
    @GetMapping("/{roleId}/own/menu")
    @ResponseBody
    public ResultBean getRoleOwnMenu(@PathVariable("roleId") Integer roleId) {
        return ResultBean.success(roleService.getMenuByRoleId(roleId));
    }

    @ApiOperation("获取角色拥有的操作权限")
    @GetMapping("/{roleId}/own/operator")
    @ResponseBody
    public ResultBean getRoleOwnOperator(@PathVariable("roleId") Integer roleId) {
        return ResultBean.success(roleService.getOperatorByRoleId(roleId));
    }
    
    
}
