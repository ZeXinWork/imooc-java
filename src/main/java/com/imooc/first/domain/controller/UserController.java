package com.imooc.first.domain.controller;


import com.alibaba.druid.util.StringUtils;
import com.imooc.first.domain.common.ApiRestResponse;
import com.imooc.first.domain.dto.LoginSuccessDto;
import com.imooc.first.domain.entity.User;
import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.request.EmailLogin;
import com.imooc.first.domain.request.GetCaptcha;
import com.imooc.first.domain.service.EmailSender;
import com.imooc.first.domain.service.EmailService;
import com.imooc.first.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    @Resource
    UserService userService;

    @Autowired
    EmailService emailService;


    @Autowired
    private EmailSender emailSender;


    @PostMapping("/register")
    public ApiRestResponse<Boolean> register(@RequestBody User user) {
        System.out.println(user.toString());
        log.info(user.toString());

        if (StringUtils.isEmpty(user.getUsername())) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }

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

    @PostMapping("/login")
    public ApiRestResponse<LoginSuccessDto> login(@RequestBody User user) {
        log.info("{登录}");
        LoginSuccessDto loginSuccessDto = null;
        try {
            loginSuccessDto = userService.login(user); // 调用业务层登录方法
        } catch (ImoocMallException e) {
            e.printStackTrace(); // 记录异常
            return ApiRestResponse.error(e.getCode(), e.getMessage()); // 返回自定义错误信息
        }

        if (loginSuccessDto == null) {
            // 如果 loginSuccessDto 为 null，可能是登录失败，返回对应的错误信息
            return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR);
        }

        // 正常返回登录成功的响应
        return ApiRestResponse.success(loginSuccessDto);
    }

    @GetMapping("/detail")
    public ApiRestResponse<User> getUser(@RequestParam("name") String name) {
        User user = userService.getUser(name);
        if (user != null) {
            return ApiRestResponse.success(user);
        }
        return ApiRestResponse.error(ImoocMallExceptionEnum.USER_NOT_FIND);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public ApiRestResponse<User> getUserInfo() {
        return ApiRestResponse.success(userService.getUserInfo());
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public ApiRestResponse<Boolean> logout() {
        return ApiRestResponse.success(userService.logout());
    }

    @GetMapping("/captcha")
    public ApiRestResponse<Boolean> loginEmail(GetCaptcha emailLogin) {
        emailSender.sendEmail(emailLogin.getEmail());
        return ApiRestResponse.success();
    }

    @PostMapping("/email")
    public ApiRestResponse<LoginSuccessDto> emailLogin(@RequestBody EmailLogin emailLogin) {
        return ApiRestResponse.success(emailService.loginEmail(emailLogin));
    }
}
