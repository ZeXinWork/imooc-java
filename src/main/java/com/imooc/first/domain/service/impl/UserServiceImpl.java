package com.imooc.first.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.dto.LoginSuccessDto;
import com.imooc.first.domain.entity.User;
import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.mapper.UserMapper;
import com.imooc.first.domain.service.UserService;
import com.imooc.first.domain.utils.JwtUtils;
import com.imooc.first.domain.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    PasswordUtils passwordUtils;

    @Autowired
    JwtUtils jwtUtils;

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
        boolean result = passwordUtils.verifyPassword(user.getPassword(), existingUser.getPassword());

        if (!result) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        String token = jwtUtils.generateToken(existingUser.getUsername());

        existingUser.setPassword(null);

        LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
        loginSuccessDto.setUser(existingUser);
        loginSuccessDto.setToken(token);

        return loginSuccessDto;
    }


    public User getSame(String name) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, name));
    }
}
