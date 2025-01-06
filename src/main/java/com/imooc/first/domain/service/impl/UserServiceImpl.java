package com.imooc.first.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.entity.User;
import com.imooc.first.domain.exception.ImoocMallException;
import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import com.imooc.first.domain.mapper.UserMapper;
import com.imooc.first.domain.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    //用户注册
    @Override
    public Boolean register(User user) throws ImoocMallException {
        User existingUser = this.getSame(user.getUsername());
        if (existingUser != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        return this.saveOrUpdate(user);
    }


    public User getSame(String name) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, name));
    }
}
