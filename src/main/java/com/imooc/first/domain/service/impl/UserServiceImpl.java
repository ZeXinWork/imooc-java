package com.imooc.first.domain.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.dto.LoginSuccessDto;
import com.imooc.first.domain.entity.User;
import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.mapper.UserMapper;
import com.imooc.first.domain.service.UserService;
import com.imooc.first.domain.utils.JwtUtils;
import com.imooc.first.domain.utils.PasswordUtils;
import com.imooc.first.domain.utils.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    PasswordUtils passwordUtils;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    TokenService tokenService;

    //用户注册
    @Override
    public Boolean register(User user) throws ImoocMallException {
        User existingUser = this.getSame(user.getUsername());
        if (existingUser != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        user.setPassword(passwordUtils.encryptPassword(user.getPassword()));

        return this.saveOrUpdate(user);
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @Override
    public LoginSuccessDto login(User user) throws ImoocMallException {
        User existingUser = this.getSame(user.getUsername());
        if (existingUser == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.USER_NOT_EXISTED);
        }
        System.out.println("password:" + existingUser);
        boolean result = passwordUtils.verifyPassword(user.getPassword(), existingUser.getPassword());
        if (!result) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        String token = jwtUtils.generateToken(existingUser.getUsername());

        boolean b = tokenService.saveUserToken(existingUser.getId(), token);
        System.out.println(b + "token设置");
        log.info("token设置{}", b);

        existingUser.setPassword(null);

        LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
        loginSuccessDto.setUser(existingUser);
        loginSuccessDto.setToken(token);

        return loginSuccessDto;
    }

    @Override
    public User getUser(String name) {
        return this.getSame(name);
    }


    @Override
    public User getUserByEmail(String email) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
    }


    public User getSame(String name) {
        User existingUser = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, name));
//        if (existingUser != null) {
//            existingUser.setPassword(null);
//        }
        return existingUser;
    }


    @Override
    public User getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        if (!StringUtils.isEmpty(username)) {
            User existingUser = this.getSame(username);
            if (existingUser != null) {
                existingUser.setPassword(null);
            }
            return existingUser;
        } else {
            throw new ImoocMallException(ImoocMallExceptionEnum.UNAUTHORIZED);
        }
    }

    @Override
    public Boolean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User user = this.getSame(username);
        if (user != null) {
            tokenService.removeUserToken(user.getId());
            return true;
        }
        return false;
    }


}
