package com.hhee.operation.core.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description 自定义Realm，继承AuthorizingRealm类，重写认证和授权方法
 * @ClassName UserRealm
 */
public class UserRealm extends AuthorizingRealm {
    @Value("${my.name}")
    private String myName;
    @Value("${my.psw}")
    private String myPsw;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();//拿到用户
//        info.addStringPermission("user:edit");
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 将token进行转换，token是前端页面传过来的用户名+密码的加密封装
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 用户名校验
        // 用户名和密码，应该从数据库中取，这里用配置文件数据代替
        if(!upToken.getUsername().equals(myName)) {
            return null;  // 返回null的话，会自动抛出UnknownAccountException异常
        }
        // 密码校验，Shiro自动完成
        return new SimpleAuthenticationInfo(myName,myPsw,upToken.getUsername());
    }
}

