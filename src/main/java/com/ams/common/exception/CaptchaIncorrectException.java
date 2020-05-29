package com.ams.common.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @Author: WuWeiquan
 * @Date: 2020/5/23 22:21
 */
public class CaptchaIncorrectException extends AuthenticationException {
    private static final long serialVersionUID = 2682461331543282364L;

    public CaptchaIncorrectException() {
        super();
    }
}
