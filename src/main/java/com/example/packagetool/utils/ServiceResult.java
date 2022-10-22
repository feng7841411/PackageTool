package com.example.packagetool.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author feng
 * @date 2022/10/4
 * @time 17:29
 * @apiNote
 *
 * 2022年10月4日 17点29分
 * 参考控制层的封装方法
 * 因为Service层的处理结果，可能成功也可能失败，而且经常会有返回值出来，因此考虑统一封装逻辑层的返回结果
 *
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult {

    private String code;
    private String msg;
    private Object data;

    /**
     * 逻辑层正常执行，返回成功，不需要传递什么数值
     * @return
     */
    public static ServiceResult success() {
        return new ServiceResult(Constants.CODE_200,"",null);
    }


    /**
     * 逻辑层成功执行，携带一条消息msg和数据data返回
     * @param msg
     * @param data
     * @return
     */
    public static ServiceResult success(String msg, Object data) {
        return new ServiceResult(Constants.CODE_200,msg,data);
    }

    /**
     * 逻辑层错误执行，没有返回什么数据（但是这种要么逻辑层自己打了日志，不然一般用得比较少吧？调用者一般希望知道错误原因）
     * @return
     */
    public static ServiceResult error() {
        return new ServiceResult(Constants.CODE_500,"",null);
    }

    /**
     * 逻辑层错误执行，这个错误码，直接都标了500，感觉这个目前用的比较少，我大部分只用到了msg和data，错误码用的约定比较少
     * @param msg
     * @param data
     * @return
     */
    public static ServiceResult error(String msg, Object data) {
        return new ServiceResult(Constants.CODE_500,msg,data);
    }


}
