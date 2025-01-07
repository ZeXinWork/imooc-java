package com.imooc.first.domain.dto;

import com.imooc.first.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessDto {

    //登录的用户信息
    private User user;

    //用户token
    private String token;
}
