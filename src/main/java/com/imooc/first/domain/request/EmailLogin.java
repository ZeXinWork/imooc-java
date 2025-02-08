package com.imooc.first.domain.request;

import lombok.Data;

@Data
public class EmailLogin {
    private String email;
    private String code;
}
