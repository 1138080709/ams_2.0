package com.ams.common.shiro.realm;

import com.ams.common.shiro.AmsPropcertis;
import com.ams.common.utils.ShiroUtils;
import com.ams.system.entity.User;
import com.ams.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class UserNameRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserNameRealm.class);

    @Resource
    private AmsPropcertis amsPropcertis;

    @Resource
    private UserService userService;

    @Resource
    private SessionDAO sessionDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("从数据库获取信息");
        User user = (User) principals.getPrimaryPrincipal();

        String username = user.getUsername();

        Set<String> roles = userService.selectRoleNameByUsername(username);
        Set<String> perms = userService.selectPermsByUsername(username);

        log.info("用户的角色：" + roles);
        log.info("用户的权限：" + perms);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(perms);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("从数据库获取认证信息");
        String username = (String) token.getPrincipal();
        User user = userService.selectOneByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();
        }
        // 如果账号被锁住，则抛出异常(超级管理员除外)
        if (ShiroUtils.USER_LOCK == user.getStatus() && !amsPropcertis.getSuperAdminUsername().equals(username)) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), super.getName());
    }

    public void clearAllAuthCache() {
        // 获取所有session
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            // 获取session登录信息
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(obj instanceof SimplePrincipalCollection) {
                // 强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                User user = new User();
                BeanUtils.copyProperties(spc.getPrimaryPrincipal(),user);
                this.doClearCache(spc);
            }
        }
    }

    public void clearAuthCacheByUserId(Integer userId) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(obj instanceof SimplePrincipalCollection) {
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                User user = new User();
                BeanUtils.copyProperties(spc.getPrimaryPrincipal(),user);
                if(userId.equals(user.getUserId())){
                    this.doClearCache(spc);
                }
            }
        }
    }
}
