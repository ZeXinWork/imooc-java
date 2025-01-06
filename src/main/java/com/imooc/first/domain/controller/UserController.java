package com.imooc.first.domain.controller;


import com.imooc.first.domain.common.ApiRestResponse;
import com.imooc.first.domain.entity.User;
import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    UserService userService;


    @PostMapping("/register")
    public ApiRestResponse<Boolean> register(@RequestBody User user) {
        try {
            Boolean res = userService.register(user);
            if (res) {
                return ApiRestResponse.success(true); // 注册成功返回 true
            }
            return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR); // 注册失败返回错误信息
        } catch (ImoocMallException e) {
            return ApiRestResponse.error(e.getCode(), e.getMessage()); // 捕获自定义异常并返回错误响应
        }
    }
}
