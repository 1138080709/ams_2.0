package com.ams.common.interceptor;

import com.ams.common.utils.IPUtils;
import com.ams.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MDC拦截器，用于将当前操作人的用户名及IP添加到MDC中，以在日志中显示
 */
@Component
public class LogMDCInterceptor implements HandlerInterceptor {
    
    private static final String MDC_USERNAME = "username";
    
    private static final String IP = "ip";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果已经登录，则获取当前登录者的用户名放入MDC
        String username = "";
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user != null) {
            username = user.getUsername();
        }

        MDC.put(IP, IPUtils.getIpAddr());
        MDC.put(MDC_USERNAME,username);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String username = MDC.get(MDC_USERNAME);
        MDC.remove(username);
    }
}
