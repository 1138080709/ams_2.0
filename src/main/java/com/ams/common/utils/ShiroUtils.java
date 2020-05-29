package com.ams.common.utils;

import com.ams.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.stereotype.Component;

@Component
public class ShiroUtils {

    public static final Integer USER_LOCK = 0;

    /**
     * 获取当前登录用户
     */
    public static User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new UnauthenticatedException("未登录被拦截");
        }
        return user;
    }
}
