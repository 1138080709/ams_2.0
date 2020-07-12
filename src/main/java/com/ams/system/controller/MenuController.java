package com.ams.system.controller;

import com.ams.common.annotation.RefreshFilterChain;
import com.ams.common.utils.PageResultBean;
import com.ams.common.utils.ResultBean;
import com.ams.system.entity.Menu;
import com.ams.system.entity.User;
import com.ams.system.service.MenuService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/24 15:54
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @Resource
    private MenuService menuService;

    @GetMapping("/index")
    public String index() {
        return "menu/menu-list";
    }
    
    @ApiOperation("获取菜单列表")
    @GetMapping("/list")
    @ResponseBody
    public ResultBean getList(@RequestParam(value = "parentId",required = false) Integer parentId) {
        List<Menu> menuList = menuService.selectByParentId(parentId);
        return ResultBean.success(menuList);
    }
    
    @GetMapping
    public String add() {
        return "menu/menu-add";
    }
    
    @ApiOperation("新增菜单")
    @RefreshFilterChain
    @PostMapping
    @ResponseBody
    public ResultBean add(Menu menu) {
        return ResultBean.success(menuService.add(menu));
    }

    @GetMapping("/{menuId}")
    public String edit(@PathVariable("menuId") Integer menuId, Model model) {
        Menu menu = menuService.selectMenuByMenuId(menuId);
        model.addAttribute("menu",menu);
        return "menu/menu-add";
    }
    
    @ApiOperation("编辑菜单")
    @RefreshFilterChain
    @PutMapping
    @ResponseBody
    public ResultBean edit(Menu menu) {
        menuService.edit(menu);
        return ResultBean.success();
    }

    @ApiOperation("删除菜单")
    @RefreshFilterChain
    @DeleteMapping("/{menuId}")
    @ResponseBody
    public ResultBean del(@PathVariable("menuId") Integer menuId) {
         menuService.delete(menuId);
        return ResultBean.success();
    }

//    @ApiOperation("获取指定菜单")
//    @GetMapping("/{menuId}")
//    @ResponseBody
//    public ResultBean getMenu(@PathVariable("menuId") Integer menuId) {
//        return ResultBean.success(menuService.selectMenuByMenuId(menuId));
//    }

//    @ApiOperation("获取当前用户的菜单")
//    @GetMapping
//    @ResponseBody
//    public ResultBean getMenu() {
//        return ResultBean.success(menuService.selectCurrentUserMenuTree());
//    }

    @GetMapping("/tree")
    @ResponseBody
    public ResultBean tree() {
        return ResultBean.success(menuService.getAllTree());
    }

    @GetMapping("/tree/root")
    @ResponseBody
    public ResultBean treeAndRoot() {
        return ResultBean.success(menuService.getAllMenuTreeAndRoot());
    }

    @GetMapping("/tree/root/operator")
    @ResponseBody
    public ResultBean menuAndCountOperatorTreeAndRoot() {
        return ResultBean.success(menuService.getAllMenuAndCountOperatorTreeAndRoot());
    }
    
    @ApiOperation("调整部门排序")
    @PostMapping("/swap")
    @ResponseBody
    public ResultBean swapSort(Integer currentId, Integer swapId) {
        menuService.swapSort(currentId,swapId);
        return ResultBean.success();
    }
}
