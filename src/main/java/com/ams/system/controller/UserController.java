package com.ams.system.controller;

import com.ams.common.utils.PageResultBean;
import com.ams.common.utils.ResultBean;
import com.ams.common.validator.Create;
import com.ams.common.validator.Update;
import com.ams.system.entity.User;
import com.ams.system.service.RoleService;
import com.ams.system.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/24 15:52
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @GetMapping("/index")
    public String index() {
        return "user/user-list";
    }

    @ApiOperation(value = "返回用户信息")
    @GetMapping
    @ResponseBody
    public ResultBean getUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return ResultBean.successData(user);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResultBean delete(@PathVariable("userId") int userId) {
        userService.delete(userId);
        return ResultBean.success();
    }
    
    @ApiOperation(value = "重置密码", notes = "管理员专用权限")
    @PutMapping("/{userId}/reset")
    @ResponseBody
    public ResultBean resetPassword(@PathVariable("userId") int userId) {
        userService.resetPasswordByUserId(userId);
        return ResultBean.success();
    }

    @GetMapping("/update")
    public String updatePassword() {
        return "user/user-update-pwd";
    }

    @ApiOperation(value = "修改密码", notes = "用户通用权限")
    @PutMapping("/update")
    @ResponseBody
    public ResultBean updatePassword(String oldPassword, String newPassword) {
        userService.updatePassword(oldPassword, newPassword);
        return ResultBean.success();
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("roles",roleService.selectAll());
        return "user/user-add";
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("roleIds", userService.selectRoleIdByUserId(userId));
        model.addAttribute("user", userService.selectOne(userId));
        model.addAttribute("roles", roleService.selectAll());
        return "user/user-add";
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    @ResponseBody
    public ResultBean add(@Validated(Create.class) User user, @RequestParam(value = "role[]", required = false) Integer[] roleIds) {
        return ResultBean.success(userService.add(user,roleIds));
    }

    @ApiOperation(value = "编辑用户")
    @PutMapping
    @ResponseBody
    public ResultBean edit(@Validated(Update.class) User user, @RequestParam(value = "role[]", required = false) Integer[] roleIds) {
        userService.edit(user,roleIds);
        return ResultBean.success();
    }

    @ApiOperation(value = "禁用用户")
    @PostMapping("/{userId}/disable")
    @ResponseBody
    public ResultBean disable(@PathVariable("userId") Integer userId) {
        return ResultBean.success(userService.disableUserByUserId(userId));
    }

    @ApiOperation(value = "激活用户")
    @PostMapping("/{userId}/enable")
    @ResponseBody
    public ResultBean enable(@PathVariable("userId") Integer userId) {
        return ResultBean.success(userService.enableUserByUserId(userId));
    }

    @ApiOperation(value = "获取用户列表", notes = "分页")
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<User> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        User userQuery) {
        List<User> users = userService.selectAllWithQuery(page,limit,userQuery);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }
}
