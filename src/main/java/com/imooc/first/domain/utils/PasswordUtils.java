package com.imooc.first.domain.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 密码加密、验证工具类
@Component  // 使其成为 Spring 管理的 Bean
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    // 构造方法注入
    @Autowired
    public PasswordUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 密码加密方法
    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);  // 使用 PasswordEncoder 进行加密
    }

    // 密码验证方法
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);  // 验证密码
    }
}
