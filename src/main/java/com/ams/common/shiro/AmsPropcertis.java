package com.ams.common.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ams")
public class AmsPropcertis {
    private String superAdminUsername;
    private Integer retryCount;
    private Boolean loginVerify;
    private Integer retryTimeout;
    private Integer sessionTimeout;
    private Integer permsCacheTimeout;

    public String getSuperAdminUsername() {
        return superAdminUsername;
    }

    public void setSuperAdminUsername(String superAdminUsername) {
        this.superAdminUsername = superAdminUsername;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Boolean getLoginVerify() {
        return loginVerify;
    }

    public void setLoginVerify(Boolean loginVerify) {
        this.loginVerify = loginVerify;
    }

    public Integer getRetryTimeout() {
        return retryTimeout;
    }

    public void setRetryTimeout(Integer retryTimeout) {
        this.retryTimeout = retryTimeout;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Integer getPermsCacheTimeout() {
        return permsCacheTimeout;
    }

    public void setPermsCacheTimeout(Integer permsCacheTimeout) {
        this.permsCacheTimeout = permsCacheTimeout;
    }
}
