package com.hhee.operation.core.shiro;

import com.alibaba.fastjson.JSONObject;
import com.hhee.operation.core.http.ErrorCode;
import com.hhee.operation.core.http.HttpResult;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    /**
     * 根据请求接口路径进行验证
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws IOException
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        // 获取接口请求路径
        String servletPath = WebUtils.toHttp(request).getServletPath();
        mappedValue = new String[]{servletPath};
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 解决权限不足302问题
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() != null) {
            return true;
        } else {

            //解决 WebUtils.toHttp 往返回response写数据跨域问题
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String origin = httpRequest.getHeader("Origin");
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
            //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

            WebUtils.toHttp(response).setContentType("application/json; charset=utf-8");
            WebUtils.toHttp(response).getWriter().print(JSONObject.toJSONString(HttpResult.error(ErrorCode.FORBIDDEN,"禁止访问!")));
        }
        return false;
    }
}
