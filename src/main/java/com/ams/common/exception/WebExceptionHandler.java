package com.ams.common.exception;

import com.ams.common.utils.ResultBean;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;

/**
 * 全局异常处理
 * 尝试使用@ResponseBody+ResultBean转发异常信息
 */
@ControllerAdvice
public class WebExceptionHandler {

    @Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public String unauthenticatedException(UnauthenticatedException e) {
        return "redirect:" + shiroFilterFactoryBean.getLoginUrl();
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean noHandlerFound (NoHandlerFoundException e) {
        if (log.isDebugEnabled()) {
            log.debug("请求的地址不存在", e);
        }
        return ResultBean.error("请求的地址不存在");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean unauthorized(UnauthorizedException e) {
        if (log.isDebugEnabled()) {
            log.debug("无权限");
        }
        return ResultBean.error("无权限");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean unknownAccount(UnknownAccountException e) {
        if (log.isDebugEnabled()) {
            log.debug("账户不存在");
        }
        return ResultBean.error("账户不存在");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean incorrectCredentials(IncorrectCredentialsException e) {
        if (log.isDebugEnabled()) {
            log.debug("密码错误");
        }
        return ResultBean.error("密码错误");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean excessiveAttempts(ExcessiveAttemptsException e) {
        if (log.isDebugEnabled()) {
            log.debug("登录失败次数过多");
        }
        return ResultBean.error("登录失败次数过多,请稍后再试");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean lockedAccount(LockedAccountException e) {
        if (log.isDebugEnabled()) {
            log.debug("账号已锁定");
        }
        return ResultBean.error("账号已锁定");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean captchaIncorrect(CaptchaIncorrectException e) {
        if (log.isDebugEnabled()) {
            log.debug("验证码错误");
        }
        return ResultBean.error("验证码错误");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean duplicateName(DuplicateNameException e) {
        if(log.isDebugEnabled()) {
            log.debug("用户名已存在");
        }
        return ResultBean.error("用户名已存在");
    }
    
    @ExceptionHandler
    @ResponseBody
    public ResultBean duplicateDigits(DuplicateDigitsException e) {
        if(log.isDebugEnabled()) {
            log.debug("学号已存在");
        }
        return ResultBean.error("学号已存在");
    }

    @ExceptionHandler
    @ResponseBody
    public ResultBean noFillStudentInfo(NoFillStudentInfoException e) {
        if(log.isDebugEnabled()) {
            log.debug("该用户还未完善学生信息");
        }
        return ResultBean.error("该用户还未完善学生信息，请尽快填写");
    }
    
    @ExceptionHandler
    @ResponseBody
    public ResultBean DuplicateFillStudentInfo(DuplicateFillStudentInfoException e) {
        if(log.isDebugEnabled()) {
            log.debug("重复完善学生信息");
        }
        return ResultBean.error("重复完善学生信息");
    }
    
    @ExceptionHandler
    @ResponseBody
    public ResultBean illegalArgument(IllegalArgumentException e) {
        if(log.isDebugEnabled()) {
            log.debug(e.getMessage(), e);
        }
        return ResultBean.error(e.getMessage());
    }
}
