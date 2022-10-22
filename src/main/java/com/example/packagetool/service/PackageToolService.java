package com.example.packagetool.service;

import com.example.packagetool.utils.ServiceResult;

import java.util.Map;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 12:20
 * @Description:
 */
public interface PackageToolService {

    /**
     * 打包封装的核心方法
     * @param params
     * @return
     */
    public ServiceResult packageZip(Map<String, Object> params) throws Exception;


}
