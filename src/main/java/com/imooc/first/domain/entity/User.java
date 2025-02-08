package com.imooc.first.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述：用户实体类，映射数据库表 imooc_mall_user
 */
@Data
@TableName("imooc_mall_user") // 指定数据库表名
public class User {

    @TableId(value = "id", type = IdType.AUTO) // 主键，使用数据库自增策略
    private Integer id; // 用户ID

    private String username; // 用户名，唯一，非空

    private String password; // 用户密码，非空

    private String signature; // 用户签名

    private Integer role; // 用户角色，非空

    private LocalDateTime createTime; // 创建时间，默认当前时间

    private LocalDateTime updateTime; // 更新时间，自动更新为当前时间

    private String email; //邮箱

}
