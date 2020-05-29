package com.ams.system.controller;

import com.ams.common.exception.CaptchaIncorrectException;
import com.ams.common.shiro.AmsPropcertis;
import com.ams.common.utils.CaptchaUtil;
import com.ams.common.utils.ResultBean;
import com.ams.system.entity.User;
import com.ams.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/23 21:22
 */
@Controller
public class LoginController {
    
    @Resource
    private UserService userService;
    
    @Resource
    private AmsPropcertis amsPropcertis;
    
    @GetMapping("/login")
    @ResponseBody
    public ResultBean login() {
        return ResultBean.error("无权限");
    }
    
    @ApiOperation(value = "登录",notes = "支持验证码校验,暂未开启")
    @PostMapping("/login")
    @ResponseBody
    public ResultBean login(User user, @RequestParam(value = "captcha",required = false) String captcha) {
        Subject subject = SecurityUtils.getSubject();
        
        if(amsPropcertis.getLoginVerify()) {
            String realCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute("captcha");
            // session 中的验证码过期了
            if(realCaptcha == null || !realCaptcha.equals(captcha.toLowerCase())) {
                throw new CaptchaIncorrectException();
            }
        }

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        subject.login(token);
        userService.updateLastLoginTimeByUsername(user.getUsername());
        return ResultBean.success("登录成功");
    }

    @ApiOperation(value = "注销")
    @GetMapping("/logout")
    @ResponseBody
    public ResultBean logout() {
        SecurityUtils.getSubject().logout();
        return ResultBean.success("账号注销成功");
    }

    @ApiOperation(value = "验证码",notes = "直接返回验证码图片")
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) throws IOException {
        CaptchaUtil.Captcha captcha = CaptchaUtil.createCaptcha(140,38,4,10,30);
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("captcha",captcha.getCode());
        
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(captcha.getImage(), "png", os);
    }
}