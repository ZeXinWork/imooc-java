package com.imooc.first.domain.common;

import com.imooc.first.domain.exception.ImoocMallExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 描述：     通用返回对象
 */
@Data
@AllArgsConstructor
public class ApiRestResponse<T> {

    private Integer status;

    private String msg;

    private T data;

    private static final int OK_CODE = 200;

    private static final String OK_MSG = "SUCCESS";

    // 手动实现无参构造函数，设置默认值
    public ApiRestResponse() {
        this.status = OK_CODE;
        this.msg = OK_MSG;
    }

    public static <T> ApiRestResponse<T> success() {
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T result) {
        return new ApiRestResponse<>(OK_CODE, OK_MSG, result);
    }

    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code, msg, null);
    }

    public static <T> ApiRestResponse<T> error(ImoocMallExceptionEnum ex) {
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg(), null);
    }
}
