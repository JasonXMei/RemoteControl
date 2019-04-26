package com.jason.common.enums;

import lombok.Getter;

@Getter
public enum HttpStatus {

    OK(200, "{%s}成功!"),
    USER_NOT_MATCH(410, "用户名密码匹配数据库失败!"),
    USER_NOT_PERMISSION(412, "用户权限不足!"),
    JWT_VERIFY_FAILED(420, "token验证失败!"),
    PARAMETER_MISSING(430, "{%s}请求参数缺失!"),
    PARAMETER_INVALID(430, "{%s}请求参数无效{%s}"),
    SERVER_INNER_ERROR(440, "服务器内部错误{%s}，请稍候重试!"),
    INTERCEPTOR_NOT_ALLOW(450, "用户登陆已失效或用户权限不足!"),
    GLOBALEXCEPTION_ERROR(451, "服务器太忙了,请稍后重试!");


    public int status;
    public String message;

    HttpStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static HttpStatus getHttpStatusByStatus(Integer status){
        for(HttpStatus e : HttpStatus.values()){
            if(e.getStatus() == status){
                return e;
            }
        }
        return null;
    }
}
