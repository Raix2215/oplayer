package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 标准返回结果
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    Integer code;
    String msg;
    Object data;

    public static Result success(Object data) {
        Result result = Result.success();
        result.setData(data);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(1);
        result.setMsg("success");
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("err");
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }
}


