package com.ams.system.service;

import cn.hutool.core.util.ReflectUtil;
import com.ams.common.shiro.realm.UserNameRealm;
import com.ams.system.entity.Menu;
import com.ams.system.entity.Operator;
import org.apache.shiro.ShiroException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiroService {

    private static final Logger log = LoggerFactory.getLogger(ShiroService.class);

    @Lazy
    @Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Resource
    private UserNameRealm userNameRealm;

    @Resource
    private MenuService menuService;

    @Resource
    private OperatorService operatorService;

    /**
     * 从数据库加载用户拥有的菜单权限和API权限
     * 注：先获取所有的菜单权限和API权限，以便后续单个用户权限的匹配与认证
     *
     * @return
     */
    public Map<String, String> getUrlPermsMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 系统默认过滤器
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/captcha", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/403", "anon");
        filterChainDefinitionMap.put("/404", "anon");
        filterChainDefinitionMap.put("/500", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/security", "anon");


        List<Menu> menuList = menuService.getLeafNodeMenu();
        for (Menu menu : menuList) {
            String url = menu.getUrl();
            if (url != null) {
                String perms = "perms[" + menu.getPerms() + "]";
                filterChainDefinitionMap.put(url, perms);
            }
        }

        List<Operator> operatorList = operatorService.selectAll();
        for (Operator operator : operatorList) {
            String url = operator.getUrl();
            if (url != null) {
                if (operator.getHttpMethod() != null && !"".equals(operator.getHttpMethod())) {
                    url += ("==" + operator.getHttpMethod());
                }
                String perms = "perms[" + operator.getPerms() + "]";
                filterChainDefinitionMap.put(url, perms);
            }
        }
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    public void updateFilterChain() {
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new ShiroException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(getUrlPermsMap());
            userNameRealm.clearAllAuthCache();

            // 清除每个Filter中的appliedPaths信息
            for (Map.Entry<String, Filter> filterEntry : manager.getFilters().entrySet()) {
                if (filterEntry.getValue() instanceof PathMatchingFilter) {
                    PathMatchingFilter filter = (PathMatchingFilter) filterEntry.getValue();
                    Map<String, Object> appliedPaths = (Map<String, Object>) ReflectUtil.getFieldValue(filter, "appliedPaths");
                    synchronized (appliedPaths) {
                        appliedPaths.clear();
                    }
                }
            }

            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            log.info("更新Shiro过滤器");
        }
    }
}