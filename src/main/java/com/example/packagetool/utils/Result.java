package com.example.packagetool.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/14
 * Time: 14:53
 * Description:
 *
 * @author feng
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(Constants.CODE_200,"",null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result success(String msg,Object data) {
        return new Result(Constants.CODE_200,msg,data);
    }

    public static Result error() {
        return new Result(Constants.CODE_500,"系统错误",null);
    }

    public static Result error(String code, String msg) {
        return new Result(code,msg,null);
    }


}
