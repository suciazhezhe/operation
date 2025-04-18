package com.hhee.operation.core.login.contoller;

import com.hhee.operation.core.http.ErrorCode;
import com.hhee.operation.core.http.HttpResult;
import com.hhee.operation.core.login.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public HttpResult login(@RequestBody User user){
        SecurityUtils.getSubject().logout();// 此行代码用于修改会话标识未更新的BUG，作用清空登录之前产生的session信息
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
            subject.login(token);
            String sessionId = null;
            if (null != subject.getSession()){
                sessionId = (String)subject.getSession().getId();
            }
            return HttpResult.ok(sessionId);
        }catch (IncorrectCredentialsException e){
            return HttpResult.error(ErrorCode.UNAUTHORIZED,"用户名或密码错误！");
        }catch (Exception e){
            return HttpResult.error(ErrorCode.Server_Error,"账户验证失败");
        }
    }

    @GetMapping("/loginout")
    public HttpResult loginout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return HttpResult.ok();
        } catch (AuthenticationException e) {
            return HttpResult.error(ErrorCode.Server_Error,"服务器执行异常！");
        }
    }
}
