package com.ams.system.service;

import com.ams.common.shiro.realm.UserNameRealm;
import com.ams.system.dao.RoleMapper;
import com.ams.system.dao.RoleMenuMapper;
import com.ams.system.dao.RoleOperatorMapper;
import com.ams.system.dao.UserRoleMapper;
import com.ams.system.entity.Role;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/26 21:52
 */
@Service
public class RoleService {
    
    @Resource
    private RoleMapper roleDao;
    
    @Resource
    private UserRoleMapper userRoleDao;
    
    @Resource
    private RoleMenuMapper roleMenuDao;
    
    @Resource
    private RoleOperatorMapper roleOperatorDao;
    
    @Resource
    private UserNameRealm userNameRealm;
    
    public Integer add(Role role) {
        roleDao.insert(role);
        return role.getRoleId();
    }

    public void edit(Role role) {
        roleDao.updateByPrimaryKeySelective(role);
    }

    @Transactional
    public void delete(int roleId) {
        roleDao.deleteByPrimaryKey(roleId);
        userRoleDao.deleteUserRoleByRoleId(roleId);
        roleMenuDao.deleteRoleMenuByRoleId(roleId);
        roleOperatorDao.deleteRoleOperatorByRoleId(roleId);
    }

    /**
     * 分配菜单
     */
    @Transactional
    public void grantMenu(int roleId, Integer[] menuIds) {
        roleMenuDao.deleteRoleMenuByRoleId(roleId);
        if(menuIds != null && menuIds.length != 0) {
            roleMenuDao.insertRoleMenus(roleId, menuIds);
        }
        clearRoleAuthCache(roleId);
    }

    /**
     * 分配操作权限
     */
    @Transactional
    public void grantOperator(int roleId, Integer[] operatorIds) {
        roleOperatorDao.deleteRoleOperatorByRoleId(roleId);
        if(operatorIds != null && operatorIds.length != 0) {
            roleOperatorDao.insertRoleOperators(roleId, operatorIds);
        }
        clearRoleAuthCache(roleId);
    }

    /**
     * 刷新角色认证缓存
     */
    private void clearRoleAuthCache(int roleId) {
        // 获取该角色下的所有用户
        List<Integer> userIds = userRoleDao.selectUserIdByRoleId(roleId);
        // 将该角色下所有用户的认证信息缓存清空 刷新认证信息
        for (Integer userId : userIds) {
            userNameRealm.clearAuthCacheByUserId(userId);
        }
    }

    public Integer[] getMenuByRoleId(Integer roleId) {
        return roleMenuDao.getMenuByRoleId(roleId);
    }

    public Integer[] getOperatorByRoleId(Integer roleId) {
        Integer[] operatorIds = roleOperatorDao.getOperatorByRoleId(roleId);
        for(int i = 0; i < operatorIds.length; i++) {
            operatorIds[i] = operatorIds[i] + 10000;
        }
        return operatorIds;
        
    }

    public List<Role> selectAll() {
        return roleDao.selectAll();
    }

    public List<Role> selectAllWithQuery(int page, int limit, Role roleQuery) {
        PageHelper.startPage(page,limit);
        return roleDao.selectAllWithQuery(roleQuery);
    }

    public Role selectOneById(Integer roleId) {
        return roleDao.selectByPrimaryKey(roleId);
    }
}
