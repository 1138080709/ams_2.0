package com.ams.system.controller;

import com.ams.common.utils.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: WuWeiquan
 * @Date: 2020/7/11 4:23
 */
@Controller
@RequestMapping("security")
public class SecurityController {
    /**
     * 判断当前登录用户是否有某项权限
     */
    @GetMapping("/hasPermission/{perms}")
    @ResponseBody
    public ResultBean hasPermission(@PathVariable("perms") String perms) {
        return ResultBean.success(SecurityUtils.getSubject().isPermitted(perms));
    }
}
