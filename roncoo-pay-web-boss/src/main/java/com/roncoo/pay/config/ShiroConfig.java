package com.roncoo.pay.config;

import com.roncoo.pay.permission.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.roncoo.pay.permission.shiro.filter.RcCaptchaValidateFilter;
import com.roncoo.pay.permission.shiro.filter.RcFormAuthenticationFilter;
import com.roncoo.pay.permission.shiro.realm.OperatorRealm;
import com.roncoo.pay.permission.shiro.spring.SpringCacheManagerWrapper;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 缓存管理器
     *
     * @param ehCacheCacheManager eh缓存管理器
     * @return 缓存管理器
     */
    @Bean(name = "springCacheManagerWrapper")
    public SpringCacheManagerWrapper springCacheManagerWrapper(@Qualifier("ehCacheCacheManager") EhCacheCacheManager ehCacheCacheManager) {
        SpringCacheManagerWrapper springCacheManagerWrapper = new SpringCacheManagerWrapper();
        springCacheManagerWrapper.setCacheManager(ehCacheCacheManager);
        return springCacheManagerWrapper;
    }

    /**
     * 凭证匹配器，做登录次数验证，和密码匹配验证
     *
     * @param springCacheManagerWrapper
     * @return 凭证匹配器
     */
    @Bean(name = "credentialsMatcher")
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(@Qualifier("springCacheManagerWrapper") SpringCacheManagerWrapper springCacheManagerWrapper) {
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(springCacheManagerWrapper);
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
        retryLimitHashedCredentialsMatcher.setHashIterations(2);
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

        return retryLimitHashedCredentialsMatcher;
    }

    /**
     * 自定义的user Realm实现
     *
     * @param retryLimitHashedCredentialsMatcher 凭证匹配器
     * @return 自定义Realm
     */
    @Bean(name = "userRealm")
    public OperatorRealm operatorRealm(@Qualifier("credentialsMatcher") RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher) {
        OperatorRealm operatorRealm = new OperatorRealm();
        operatorRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher);
        operatorRealm.setCachingEnabled(false);
        return operatorRealm;
    }

    /**
     * 安全管理器
     *
     * @param operatorRealm 自定义Realm
     * @return 安全管理器
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") OperatorRealm operatorRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(operatorRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     *
     * @param defaultWebSecurityManager 安全管理器
     * @return 相当于调用SecurityUtils.setSecurityManager(securityManager)
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(defaultWebSecurityManager);
        return methodInvokingFactoryBean;
    }

    /**
     * 基于Form表单的身份验证过滤器，为了控制验证码
     * 注意：该验证器不能注册为bean，否则会导致该验证其注册两遍，在访问的时候会抛异常报错
     *
     * @return 表单的身份验证过滤器
     */
    public RcFormAuthenticationFilter rcFormAuthenticationFilter() {
        RcFormAuthenticationFilter rcFormAuthenticationFilter = new RcFormAuthenticationFilter();
        rcFormAuthenticationFilter.setUsernameParam("loginName");
        rcFormAuthenticationFilter.setPasswordParam("roncooPwd");
        rcFormAuthenticationFilter.setRememberMeParam("rememberMe");
        rcFormAuthenticationFilter.setLoginUrl("/login");
        rcFormAuthenticationFilter.setFailureKeyAttribute("shiroLoginFailure");
        return rcFormAuthenticationFilter;
    }

    /**
     * 验证码验证过滤器
     *
     * @return 验证码验证过滤器
     */
    @Bean(name = "rcCaptchaValidateFilter")
    public RcCaptchaValidateFilter rcCaptchaValidateFilter() {
        RcCaptchaValidateFilter rcCaptchaValidateFilter = new RcCaptchaValidateFilter();
        rcCaptchaValidateFilter.setCaptchaEbabled(true);
        rcCaptchaValidateFilter.setCaptchaParam("captchaCode");
        rcCaptchaValidateFilter.setFailureKeyAttribute("shiroLoginFailure");
        return rcCaptchaValidateFilter;
    }

    /**
     * Shiro主过滤器本身功能十分强大,其强大之处就在于它支持任何基于URL路径表达式的、自定义的过滤器的执行
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截,Shiro对基于Spring的Web应用提供了完美的支持
     *
     * @param defaultWebSecurityManager 安全管理器
     * @param rcCaptchaValidateFilter   验证码验证过滤器
     * @return Shiro主过滤器
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager, @Qualifier("rcCaptchaValidateFilter") RcCaptchaValidateFilter rcCaptchaValidateFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/system/unauthorized.jsp");

        Map<String, Filter> filters = new LinkedMap();
        filters.put("authc", rcFormAuthenticationFilter());
        filters.put("rcCaptchaValidate", rcCaptchaValidateFilter);
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/rcCaptcha*", "anon");
        filterChainDefinitionMap.put("/system/unauthorized.jsp", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/dwz/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/login", "rcCaptchaValidate,authc");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator app=new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;

    }
}
