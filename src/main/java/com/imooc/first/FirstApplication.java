package com.imooc.first;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@MapperScan("com.imooc.first.domain.mapper")
public class FirstApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }
}
