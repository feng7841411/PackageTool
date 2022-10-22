package com.example.packagetool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/8/4
 * Time: 16:04
 * Description:
 *
 * 封装工具前端传入的第一个部分表单的内容，规则、合法性检验在前端已处理
 * @author feng
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicInfoForm {
    /**
     * 服务中文名，前端传入，必须是中文
     */
    private String softwareCnName;

    /**
     * 服务组名，前端传入，这个值目前不是必填项
     */
    private String softwareGroupName;

    /**
     * 服务运行环境，前端传过来，目前有3个可能值，x86、arm、others
     */
    private String softwareEnvironment;

    /**
     * 服务唯一标识，前端当做4段填，后端这个softwareId把它们拼接起来
     */
    private String softwareUniqueName;
    private String domainName;
    private String softwareName;
    private String serviceName;
    private String languageName;

    /**
     * 服务版本号，前端填写3段式，后端softwareVersion进行拼接
     */
    private String softwareVersion;
    private String majorVersionNumber;
    private String minorVersionNumber;
    private String revisionNumber;


    private String cpuRequests;
    private String memoryRequests;

}
