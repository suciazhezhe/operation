package com.hhee.operation.core.config;

import com.hhee.operation.core.shiro.CustomPermissionsAuthorizationFilter;
import com.hhee.operation.core.shiro.MySessionManager;
import com.hhee.operation.core.shiro.UserFormAuthenticationFilter;
import com.hhee.operation.core.shiro.UserRealm;
import lombok.Data;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * @Description Shiro的配置类
 * @ClassName ShiroConfig
 */
@Configuration
@Data
public class ShiroConfig {

    /*
     * @description: 注入ShiroFilterFactoryBean的bean,需要依赖DefaultWebSecurityManager对象
     * @param: [defaultWebSecurityManager]
     * @return: org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        // 设置安全管理器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //增加自定义过滤
        Map<String, Filter> filters = new HashMap<>(5);
        filters.put("authc", new UserFormAuthenticationFilter());
        filters.put("perms", new CustomPermissionsAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filters);
        // 添加Shiro常用的过滤器
        /**
         * anon（匿名）  org.apache.shiro.web.filter.authc.AnonymousFilter
         * authc（身份验证）       org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         * authcBasic（http基本验证）    org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
         * logout（退出）        org.apache.shiro.web.filter.authc.LogoutFilter
         * noSessionCreation（不创建session） org.apache.shiro.web.filter.session.NoSessionCreationFilter
         * perms(许可验证)  org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
         * port（端口验证）   org.apache.shiro.web.filter.authz.PortFilter
         * rest  (rest方面)  org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
         * roles（权限验证）  org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
         * ssl （ssl方面）   org.apache.shiro.web.filter.authz.SslFilter
         * member （用户方面）  org.apache.shiro.web.filter.authc.UserFilter
         * user  表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 设置登录页面，没有认证或权限的会被定位到此路径
//        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        return shiroFilterFactoryBean;
    }

    /*
     * @description: 注入UserRealm的bean
     * @param: []
     * @return:
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /*
     * @description: 注入DefaultWebSecurityManager的bean，需要依赖定义的UserRealm对象
     * @param: [userRealm]
     * @return:
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入SessionManager
        securityManager.setSessionManager(sessionManager());
        // 关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * @Description: 自定义的 shiro session 缓存管理器
     * 用于跨域等情况下获取请求头中的sessionId
     * @method: sessionManager
     **/
    @Bean
    public SessionManager sessionManager()
    {

        // 将我们继承后重写的shiro session 注册
        MySessionManager sessionManager = new MySessionManager();

//        sessionManager.setSessionDAO(redisSessionDAO());
        Collection<SessionListener> sessionListeners = new ArrayList<>();
//        sessionListeners.add(customSessionListener());
        sessionManager.setSessionListeners(sessionListeners);
        // 单位为毫秒，600000毫秒为1个小时
        sessionManager.setSessionValidationInterval(3600000 * 12);
        // 3600000 milliseconds = 1 hour
        sessionManager.setGlobalSessionTimeout(3600000 * 12);
        // 是否删除无效的，默认也是开启
        sessionManager.setDeleteInvalidSessions(true);
        // 是否开启 检测，默认开启
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 创建会话Cookie
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("WEBID");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);

        // 单位为毫秒，3600000毫秒为1个小时
        sessionManager.setSessionValidationInterval(3600000 * 12);
        // 3600000 milliseconds = 1 hour
        sessionManager.setGlobalSessionTimeout(3600000 * 12);
        // 是否删除无效的，默认也是开启
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;

    }







}

