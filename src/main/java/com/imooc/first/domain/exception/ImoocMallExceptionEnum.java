package com.imooc.first.domain.exception;

/**
 * 描述：     异常枚举
 */
public enum ImoocMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码长度不能小于8位"),
    NAME_EXISTED(10004, "不允许重名，注册失败"),
    INSERT_FAILED(10005, "插入失败，请重试"),
    WRONG_PASSWORD(10006, "密码错误"),
    NEED_LOGIN(10007, "用户未登录"),
    UPDATE_FAILED(10008, "更新失败"),
    NEED_ADMIN(10009, "无管理员权限"),
    USER_NOT_EXISTED(10010, "账号不存在"),
    USER_NOT_FIND(10011, "用户不存在"),
    USER_NOT_LOGIN(10012, "用户未登录"),
    TOKEN_EXPIRED(10013, "登录状态过期"),

    UNAUTHORIZED(401, "用户未登录"),

    SYSTEM_ERROR(20000, "系统异常"),
    VERIFICATION_CODE_EXPIRED(10014, "验证码过期"),
    VERIFICATION_CODE_ERROR(10015, "验证码错误");
//    VERIFICATION_CODE_ERROR(10014, "验证码过期");


    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
