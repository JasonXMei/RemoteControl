package com.jason.common.vo;

import com.jason.common.enums.HttpStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class JSONResult<T> {

    /**
     * 实体对象
     */
    T obj;

    /**
     * 请求状态
     */
    int status;

    /**
     * 请求结果详细描述
     */
    String description;

    public JSONResult(T obj, int status, String description) {
        this.obj = obj;
        this.status = status;
        this.description = description;
    }
}
