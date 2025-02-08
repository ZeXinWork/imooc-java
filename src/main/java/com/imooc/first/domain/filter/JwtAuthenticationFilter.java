package com.imooc.first.domain.filter;

import com.imooc.first.domain.common.ApiRestResponse;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

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
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is expired or invalid");
                return;  // 结束请求处理
            }
        } else {
            // Token 为空时，只有在需要认证的接口才抛出未登录错误
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private static final Set<String> PUBLIC_URLS = new HashSet<>(Arrays.asList(
            "/api/user/login",
            "/api/user/register",
            "/api/user/captcha",
            "/api/user/email"
    ));

    // 判断 URL 是否是需要认证的 URL
    private boolean isPublicUrl(String uri) {
        return PUBLIC_URLS.contains(uri);
    }

    // 发送自定义错误响应
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        ApiRestResponse<Object> apiRestResponse = ApiRestResponse.error(ImoocMallExceptionEnum.UNAUTHORIZED);  // 使用自定义异常枚举
        apiRestResponse.setStatus(statusCode);
        apiRestResponse.setMsg(message);

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiRestResponse));
    }
}
