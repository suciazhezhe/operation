package com.hhee.operation.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")//允许跨域访问的路径
                .allowedOriginPatterns("*")//允许跨域访问的源
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")//允许请求的方法
                .maxAge(168000)//预检测间隔时间
                .allowedHeaders("*")//允许头部设置
                .allowCredentials(true)//是否发送cookie
                .exposedHeaders("Access-Control-Allow-Headers",
                        "Access-Control-Allow-Methods",
                        "Access-Control-Allow-Origins",
                        "Access-Control-Max-Age",
                        "X-Frame-Options");
    }

}
