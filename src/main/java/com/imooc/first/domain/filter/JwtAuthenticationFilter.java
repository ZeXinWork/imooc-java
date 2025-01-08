package com.imooc.first.domain.filter;

import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.utils.JwtUtils;
import com.sun.javafx.binding.Logging;
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
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // 去掉 "Bearer " 前缀
            String username = jwtUtils.getUsernameFromToken(token);

            if (username != null && jwtUtils.validateToken(token, username)) {
                // 如果 token 合法，设置 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
            } else {
                throw new ImoocMallException(ImoocMallExceptionEnum.TOKEN_EXPIRED);
            }
        }

        if (token == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.USER_NOT_LOGIN);
        }

        filterChain.doFilter(request, response);
    }
}
