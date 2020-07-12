package com.ams.system.controller;

import com.ams.system.entity.Menu;
import com.ams.system.entity.User;
import com.ams.system.service.MenuService;
import com.ams.system.service.RoleService;
import com.ams.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/6/8 8:54
 */
@Controller
public class IndexController {
    
    @Resource
    private UserService userService;
    
    @Resource
    private RoleService roleService;
    
    @Resource
    private MenuService menuService;
    
    @GetMapping(value = {"/", "/main"})
    public String index(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Menu> menuTreeVOS = menuService.selectCurrentUserMenuTree();
        model.addAttribute("user",user);
        model.addAttribute("menus", menuTreeVOS);
        return "index";
    }
    
    @GetMapping("/welcome")
    public String welcome(Model model) {
//        int userCount = userService.count();
//        int roleCount = roleService.count();
//        int menuCount = menuService.count();
        return "welcome";
    }
}
