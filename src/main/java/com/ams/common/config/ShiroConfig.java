package com.ams.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ams.common.shiro.AmsPropcertis;
import com.ams.common.shiro.EnhanceModularRealmAuthenticator;
import com.ams.common.shiro.RestShiroFilterFactoryBean;
import com.ams.common.shiro.credential.RetryLimitHashedCredenialsMatcher;
import com.ams.common.shiro.filter.RestAuthorizationFilter;
import com.ams.common.shiro.filter.RestFormAuthenticationFilter;
import com.ams.common.shiro.realm.UserNameRealm;
import com.ams.system.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Resource
    private AmsPropcertis amsPropertis;
    
    @Lazy
    @Resource
    private ShiroService shiroService;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;
    
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RestShiroFilterFactoryBean restShiroFilterFactoryBean(SecurityManager securityManager) {
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        Map<String, Filter> filter = shiroFilterFactoryBean.getFilters();
        filter.put("authc", new RestFormAuthenticationFilter());
        filter.put("perms", new RestAuthorizationFilter());
        
        Map<String,String> urlPermsMap = shiroService.getUrlPermsMap();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(urlPermsMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 注入 securityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealms(Arrays.asList(userNameRealm()));
        // 可扩展多Realm登录校验
        ModularRealmAuthenticator authenticator = new EnhanceModularRealmAuthenticator(); //处理多Realm抛出的异常
        securityManager.setAuthenticator(authenticator);
        authenticator.setRealms(Arrays.asList(userNameRealm()));
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     * 用户名密码登录Realm
     */
    @Bean
    public UserNameRealm userNameRealm() {
        UserNameRealm userNameRealm = new UserNameRealm();
        userNameRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        userNameRealm.setCacheManager(redisCacheManager());
        return userNameRealm;
    }
    
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        return new RetryLimitHashedCredenialsMatcher("md5");
    }

    /**
     * RedisCacheManager shiro cache机制的redis支持
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(amsPropertis.getPermsCacheTimeout() == null ? 3600 : amsPropertis.getPermsCacheTimeout());
        redisCacheManager.setPrincipalIdFieldName("userId");
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost + ":" + redisPort);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * RedisSessionDAO实现了shiro sessionDAO对redis支持
     *
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(amsPropertis.getSessionTimeout() == null ? 1800 : amsPropertis.getSessionTimeout());
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionInMemoryEnabled(false);
        return redisSessionDAO;
    }

    /**
     * DefaultWebSessionManager默认使用ServletContainerSessionManager 将session交给servlet容器管理，不用我们自己再管理session。
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
    
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
