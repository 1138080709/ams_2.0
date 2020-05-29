package com.ams.common.shiro.credential;

import com.ams.common.shiro.AmsPropcertis;
import com.ams.common.utils.IPUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MD5 密码匹配器
 * <p>
 * 密码校验失败后计数，当超出 ${ams.retry-count} 次后，禁止登录 ${ams.retry-timeout} 秒.
 */
public class RetryLimitHashedCredenialsMatcher extends HashedCredentialsMatcher {

    @Resource
    private RedisTemplate<String, AtomicInteger> redisTemplate;

    @Resource
    private AmsPropcertis amsPropcertis;

    public RetryLimitHashedCredenialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        ValueOperations<String, AtomicInteger> opsForValue = redisTemplate.opsForValue();
        String username = (String) token.getPrincipal();
        String key = username + IPUtils.getIpAddr();

        // 超级管理员不进行登录次数校验
        if (!amsPropcertis.getSuperAdminUsername().equals(key)) {
            AtomicInteger retryCount = opsForValue.get(key);
            if (retryCount == null) {
                retryCount = new AtomicInteger(0);
            }

            if (retryCount.incrementAndGet() > amsPropcertis.getRetryCount()) {
                throw new ExcessiveAttemptsException();
            }

            Integer retryTimeout = amsPropcertis.getRetryTimeout() == null ? 300 : amsPropcertis.getRetryTimeout();
            opsForValue.set(key, retryCount, retryTimeout, TimeUnit.SECONDS);
        }

        // 如果登录成功，取出登录次数
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            redisTemplate.delete(key);
        }
        return matches;
    }
}
