package com.powernode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private Integer code = 200;     //状态码
    private String msg;     //消息
    private T data;     //数据

    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data) {
        return success("ok", data);
    }

    public static <T> Result<T> success() {
        return success("ok", null);
    }

    public static <T> Result<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> Result<T> success(Boolean flag) {
        return flag ? success("ok") : fail(500, "fail");
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
