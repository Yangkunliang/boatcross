package com.kelystor.boatcross.config;

import com.kelystor.boatcross.property.ShiroLdapProperties;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private ShiroLdapProperties shiroLdapProperties;

    @Bean
    public Realm realm(@Autowired LdapContextFactory contextFactory) {
        DefaultLdapRealm realm = new DefaultLdapRealm();
        realm.setCachingEnabled(true);
        realm.setContextFactory(contextFactory);
        realm.setUserDnTemplate(shiroLdapProperties.getUserDnTemplate());
        return realm;
    }

    @Bean
    public LdapContextFactory contextFactory() {
        JndiLdapContextFactory contextFactory = new JndiLdapContextFactory();
        contextFactory.setUrl(shiroLdapProperties.getUrl());
        contextFactory.setSystemUsername(shiroLdapProperties.getSystemUsername());
        contextFactory.setSystemPassword(shiroLdapProperties.getSystemPassword());
        return contextFactory;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**
         * anon: 无需认证（登录）可以访问
         * authc: 必须认证才可以访问
         * user: 如果使用rememberMe功能可以直接访问
         * perms: 该资源必须得到资源权限才可以访问
         * role: 该资源必须得到角色权限才可以访问
         */
        Map<String, String> filerMap = new LinkedHashMap<>(); // 顺序的map
        filerMap.put("/login", "anon");
        filerMap.put("/logout", "logout");
        filerMap.put("/noAuth", "anon");
        filerMap.put("/error", "anon");
        filerMap.put("/validCode", "anon");
        filerMap.put("/static/**", "anon");
        filerMap.put("/**", "authc");

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filerMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public CacheManager cacheManager() {
        // Caching isn't needed in this example, but we will use the MemoryConstrainedCacheManager for this example.
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
