package com.imooc.first.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.first.domain.dto.LoginSuccessDto;
import com.imooc.first.domain.entity.User;
import com.imooc.first.domain.exception.ImoocMallException;

public interface UserService extends IService<User> {
    Boolean register(User user) throws ImoocMallException;


    LoginSuccessDto login(User user) throws ImoocMallException;
}
