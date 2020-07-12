package com.ams.system.service;

import com.ams.common.shiro.AmsPropcertis;
import com.ams.common.utils.ShiroUtils;
import com.ams.common.utils.TreeUtil;
import com.ams.system.dao.RoleMenuMapper;
import com.ams.system.dao.MenuMapper;
import com.ams.system.dao.OperatorMapper;
import com.ams.system.entity.Menu;
import com.ams.system.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Resource
    private AmsPropcertis amsPropcertis;

    @Resource
    private MenuMapper menuDao;
    
    @Resource
    private RoleMenuMapper jobMenuDao;
    
    @Resource
    private OperatorMapper operatorDao;

    /**
     * 获取指定用户拥有的树形菜单（admin 账户拥有所有权限）
     */
    public List<Menu> selectMenuTreeVOByUsername(String username) {
        List<Menu> menus;
        if (amsPropcertis.getSuperAdminUsername().equals(username)) {
            menus = menuDao.selectAll();
        } else {
            menus = menuDao.selectMenuByUsername(username);
        }
        return toTree(menus);
    }

    public List<Menu> toTree(List<Menu> menus) {
        return TreeUtil.toTree(menus, "menuId", "parentId", "children", Menu.class);
    }

    /**
     * 获取导航菜单中的所有叶子节点
     */
    public List<Menu> getLeafNodeMenu() {
        List<Menu> allMenuTreeVO = getAllTree();
        return TreeUtil.getAllLeafNode(allMenuTreeVO);
    }

    /**
     * 获取所有菜单（树形结构）
     * @return
     */
    public List<Menu> getAllTree() {
        return menuDao.selectAllTree();
    }

    @Transactional
    public void delete(Integer menuId) {
        // 删除子菜单
        List<Integer> childIdList = menuDao.selectChildrenIdByMenuId(menuId);
        for(Integer childId : childIdList) {
            delete(childId);
        }
        menuDao.deleteByPrimaryKey(menuId);
        operatorDao.deleteByMenuId(menuId);
        jobMenuDao.deleteRoleMenuByMenuId(menuId);
    }

    @Transactional
    public Integer add(Menu menu) {
        int maxOrderNum = menuDao.selectMaxOrderNum();
        menu.setOrderNum(maxOrderNum+1);
        menuDao.insert(menu);
        return menu.getMenuId();
    }

    public void edit(Menu menu) {
        menuDao.updateByPrimaryKey(menu);
    }

    public Menu selectMenuByMenuId(Integer menuId) {
        return menuDao.selectByPrimaryKey(menuId);
    }
    
    public List<Menu> selectCurrentUserMenuTree() {
        User user = ShiroUtils.getCurrentUser();
        return selectMenuTreeVOByUsername(user.getUsername());
    }

    /**
     * 根据父ID获取所有菜单
     */
    public List<Menu> selectByParentId(Integer parentId) {
        return menuDao.selectByParentId(parentId);
    }

    /**
     * 获取所有菜单并添加根节点(树形结构)
     */
    public List<Menu> getAllMenuTreeAndRoot() {
        List<Menu> allMenuTree = getAllTree();
        return addRootNode("导航目录", 0, allMenuTree);
    }

    /**
     * 将树形结构添加到一个根节点下
     */
    private List<Menu> addRootNode(String rootName, int rootId, List<Menu> children) {
        Menu root = new Menu();
        root.setMenuId(rootId);
        root.setMenuName(rootName);
        root.setChildren(children);
        List<Menu> rootList = new ArrayList<>();
        rootList.add(root);
        return rootList;
    }

    /**
     * 获取所有菜单并统计菜单下的操作权限数(树形结构)
     */
    public List<Menu> getAllMenuAndCountOperatorTreeAndRoot() {
        return menuDao.selectAllMenuAndCountOperator();
    }

    public void swapSort(Integer currentId, Integer swapId) {
        menuDao.swapSort(currentId,swapId);
    }
}
