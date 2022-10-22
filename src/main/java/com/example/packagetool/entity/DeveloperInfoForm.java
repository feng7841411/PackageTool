package com.example.packagetool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/8/4
 * Time: 16:13
 * Description:
 *
 * 用于接收封装工具传入的第三部分表单，开发者信息
 * 这个类相对简单，而且这部分不是封装工具的必填项
 * @author feng
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperInfoForm {

    private String developerName;

    private String developerGroup;

    private String developerPhone;

}
