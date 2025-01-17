package com.imooc.first.domain.filter;

import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        // 如果是公开接口，不进行 token 校验
        if (isPublicUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 对需要认证的接口检查 token
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // 去掉 "Bearer " 前缀
            String username = jwtUtils.getUsernameFromToken(token);

            if (username != null && jwtUtils.validateToken(token, username)) {
                // 如果 token 合法，设置 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is expired or invalid");
                return;  // 结束请求处理
            }
        } else {
            // Token 为空时，只有在需要认证的接口才抛出未登录错误
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in");
            return;
        }

        filterChain.doFilter(request, response);
    }

    // 判断 URL 是否是需要认证的 URL
    private boolean isPublicUrl(String uri) {
        return uri.equals("/api/user/login") || uri.equals("/api/user/register");
    }
}
