package com.imooc.first.domain.service;

import com.imooc.first.domain.dto.LoginSuccessDto;
import com.imooc.first.domain.entity.User;
import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.request.EmailLogin;
import com.imooc.first.domain.request.GetCaptcha;
import com.imooc.first.domain.utils.JwtUtils;
import com.imooc.first.domain.utils.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    TokenService tokenService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    public String generateVerificationCode() {
        Random random = new Random();
        // 生成 1000-9999 的随机数，确保是四位数
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code); // 转换为字符串
    }


    public void sendEmail(GetCaptcha emailLogin) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);  // 发件人
        message.setTo(emailLogin.getEmail());  // 收件人
        message.setSubject("text");  // 邮件主题
        String code = generateVerificationCode();//验证码
        message.setText("欢迎登录,您的验证码为：" + code);  // 邮件内容
        System.out.println("Set Key: email:verificationCode:" + emailLogin.getEmail());
        RBucket<String> verificationCodeBucket = redissonClient.getBucket("email:verificationCode:" + emailLogin.getEmail());
        verificationCodeBucket.set(code, 60, TimeUnit.SECONDS);
        javaMailSender.send(message);
    }

    public LoginSuccessDto loginEmail(EmailLogin emailLogin) {
        // 从 Redis 获取存储的验证码
        RBucket<String> verificationCodeBucket = redissonClient.getBucket("email:verificationCode:" + emailLogin.getEmail());

        // 获取存储在 Redis 中的验证码
        String storedCode = verificationCodeBucket.get();
//        email:verificationCode:zhengzexin1998@gmail.com
        System.out.println("Get Key: email:verificationCode:" + emailLogin.getEmail());
        // 检查验证码是否存在和是否匹配
        if (storedCode == null) {
            // 如果 Redis 中没有该验证码，说明验证码已经过期或者从未生成
            throw new ImoocMallException(ImoocMallExceptionEnum.VERIFICATION_CODE_EXPIRED);
        }

        // 比较用户输入的验证码与 Redis 中存储的验证码
        boolean equals = storedCode.equals(emailLogin.getCode());


        if (!equals) {
            throw new ImoocMallException(ImoocMallExceptionEnum.VERIFICATION_CODE_ERROR);
        }
        User existingUser = userService.getUserByEmail(emailLogin.getEmail());

        if (existingUser == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.USER_NOT_FIND);
        }

        String token = jwtUtils.generateToken(existingUser.getUsername());

        tokenService.saveUserToken(existingUser.getId(), token);

        LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
        loginSuccessDto.setUser(existingUser);
        loginSuccessDto.setToken(token);
        return loginSuccessDto;
    }
}
