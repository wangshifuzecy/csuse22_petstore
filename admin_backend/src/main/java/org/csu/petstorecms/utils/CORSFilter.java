package org.csu.petstorecms.utils;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@Configuration
@WebFilter(filterName = "CORSFilter")
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(1);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 设置允许跨域访问的源，例如 "*" 表示允许所有来源
        response.setHeader("Access-Control-Allow-Origin", "*");

        // 设置允许的 HTTP 方法，例如 "GET, POST, PUT, DELETE"
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,PATCH");

        // 设置允许的自定义 HTTP 头
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization");

        // 设置响应缓存的时间，单位：秒
        response.setHeader("Access-Control-Max-Age", "3600");

        // 设置是否允许发送凭据（如 cookie 等）
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 调用 FilterChain 继续处理请求
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
