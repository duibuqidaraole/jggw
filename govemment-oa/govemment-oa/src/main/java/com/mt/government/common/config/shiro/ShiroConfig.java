package com.mt.government.common.config.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 *
 * @author g
 * @date 2019-11-01 8:54
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean factoryBean(SecurityManager securityManager) {
        // 配置过滤器
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new HashMap<>(4);
        filterMap.put("login" , new MyFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 配置拦截规则
        Map<String, String> filterRuleMap = new LinkedHashMap<>(4);
        filterRuleMap.put("/images/**" , "anon");
        filterRuleMap.put("/login" , "anon");
        filterRuleMap.put("/file/**" , "anon");
        filterRuleMap.put("/**" , "login");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return factoryBean;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setRealm(userRealm);
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean(name = "userReam")
    public UserRealm userRealm() {
        return new UserRealm();
    }
}
