package com.ams.system.service;

import com.ams.common.exception.DuplicateNameException;
import com.ams.common.exception.OldPasswordCheckException;
import com.ams.common.utils.TreeUtil;
import com.ams.system.dao.UserRoleMapper;
import com.ams.system.dao.UserMapper;
import com.ams.system.entity.Menu;
import com.ams.system.entity.User;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserService {

    @Resource
    private UserMapper userDao;

    @Resource
    private MenuService menuService;

    @Resource
    private UserRoleMapper userRoleDao;

    @Resource
    private SessionDAO sessionDAO;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * 获取用户所拥有的职位
     *
     * @param username
     * @return
     */
    public Set<String> selectRoleNameByUsername(String username) {
        return userDao.selectRoleNameByUsername(username);
    }

    /**
     * 获取用户所拥有的所有菜单权限和操作权限
     *
     * @param username 用户名
     * @return 权限标识符号列表
     */
    public Set<String> selectPermsByUsername(String username) {
        Set<String> perms = new HashSet<>();
        List<Menu> menuTreeVOs = menuService.selectMenuTreeVOByUsername(username);
        List<Menu> leafNodeMenuList = TreeUtil.getAllLeafNode(menuTreeVOs);
        for (Menu menu : leafNodeMenuList) {
            perms.add(menu.getPerms());
        }
        perms.addAll(userDao.selectOperatorPermsByUsername(username));
        return perms;
    }

    public User selectOneByUsername(String username) {
        return userDao.selectOneByUsername(username);
    }

    public void updateLastLoginTimeByUsername(String username) {
        userDao.updateLastLoginTimeByUsername(username);
    }

    public void delete(int userId) {
        userDao.deleteByPrimaryKey(userId);
    }

    public void updatePassword(String oldPassword, String newPassword) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String oldSalt = user.getSalt();
        String encryptOldPassword = new Md5Hash(oldPassword, oldSalt).toString();
        if(!encryptOldPassword.equals(user.getPassword())) {
            throw new OldPasswordCheckException();
        }
                
        String newSalt = generateSalt();
        String encryptNewPassword = new Md5Hash(newPassword, newSalt).toString();
        userDao.updatePasswordByUserId(user.getUserId(), encryptNewPassword, newSalt);
    }

    public void resetPasswordByUserId(int userId) {
        String salt = generateSalt();
        String encryptPassword = new Md5Hash("123456", salt).toString();
        userDao.updatePasswordByUserId(userId, encryptPassword, salt);
    }

    private String generateSalt() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Transactional
    public Integer add(User user, Integer[] roleIds) {
        checkUserNameExistOnCreate(user.getUsername());

        String salt = generateSalt();
        String encryptPassword = new Md5Hash(user.getPassword(), salt).toString();

        user.setSalt(salt);
        user.setPassword(encryptPassword);
        userDao.insert(user);

        grantRole(user.getUserId(), roleIds);

        return user.getUserId();

    }

    @Transactional
    public void edit(User user, Integer[] roleIds) {
        checkUserNameExistOnUpdate(user);
        userDao.updateByPrimaryKeySelective(user);
    }

    @Transactional
    public void grantRole(Integer userId, Integer[] roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            throw new IllegalArgumentException("赋予的角色数组不能为空.");
        }

        // 清空原有的职位，赋予新职位
        userRoleDao.deleteUserRoleByUserId(userId);
        userRoleDao.insertList(userId, roleIds);
    }

    /**
     * 新增时 检查用户名是否重复
     *
     * @param username
     */
    private void checkUserNameExistOnCreate(String username) {
        if (userDao.countByUsername(username) > 0)
            throw new DuplicateNameException();
    }

    /**
     * 更新时 检查用户名是否重复
     *
     * @param user
     */
    public void checkUserNameExistOnUpdate(User user) {
        if (userDao.countByUsernameNotIncludeUserId(user.getUsername(), user.getUserId()) > 0) {
            throw new DuplicateNameException();
        }
    }

    public boolean disableUserByUserId(Integer userId) {
        offlineByUserId(userId);
        return userDao.updateStatusByUserId(userId,0) == 1;
    }

    public boolean enableUserByUserId(Integer userId) {
        return userDao.updateStatusByUserId(userId,1) == 1;
    }

    /**
     * 踢出指定在线用户
     */
    public void offlineByUserId(Integer userId) {
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        for (Session session : activeSessions) {
            SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(simplePrincipalCollection != null) {
                User user = (User) simplePrincipalCollection.getPrimaryPrincipal();
                if(user != null && userId.equals(user.getUserId())) {
                    offlineBySessionId(String.valueOf(session.getId()));
                }
            }
        }
    }

    public void offlineBySessionId(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        if(session != null) {
            log.debug("踢出 sessionId 为：" + sessionId + "的用户");
            session.stop();
            sessionDAO.delete(session);
        }
    }

    public List<User> selectAllWithQuery(int page, int limit, User userQuery) {
        PageHelper.startPage(page, limit);
        return userDao.selectAllWithQuery(userQuery);
    }

    public User selectOne(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    public Integer selectRoleIdByUserId(Integer userId) {
        return userRoleDao.selectRoleIdByUserId(userId);
    }
}
