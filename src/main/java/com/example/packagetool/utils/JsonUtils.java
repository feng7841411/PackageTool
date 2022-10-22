package com.example.packagetool.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/8/5
 * Time: 10:30
 * Description:
 *
 * @author feng
 */
public class JsonUtils {
    public static String objToJson(Object obj) throws Exception {
        return JSON.toJSONString(obj);
    }

    public static <T> T jsonToObject(String jsonStr, Class<T> clazz) throws Exception {
        return JSON.parseObject(jsonStr, clazz);
    }

    public static <T> Map<String, Object> jsonToMap(String jsonStr) throws Exception {
        return JSON.parseObject(jsonStr, Map.class);
    }

    public static <T> T mapToObj(Map<?, ?> map, Class<T> clazz) throws Exception {
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    /**
     * Object 转换为 json 文件
     *
     * @param finalPath finalPath 是绝对路径 + 文件名，请确保欲生成的文件所在目录已创建好
     * @param object 需要被转换的 Object
     */
    public static void object2JsonFile(String finalPath, Object object) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(finalPath), StandardCharsets.UTF_8);
            osw.write(jsonObject.toJSONString());
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject.toJSONString());
    }

    /**
     * json 文件转换为 Object
     *
     * 2022年9月6日 22点09分
     * 这两个方法类是网上摘的，主要是需要把数据方案File读成JSON，不能用上面的那个 ObjectToJson, 那个是把File的路径来转，正确的应该是用File里的内容
     *
     * @param finalPath finalPath 是绝对路径 + 文件名，请确保欲生成的文件所在目录已创建好
     * @param targetClass 需要被转换的 json 对应的目标类
     * @param <T> 需要被转换的 json 对应的目标类
     * @return 解析后的 Object
     */
    public static <T> T jsonFile2Object(String finalPath, Class<T> targetClass) {
        String jsonString;
        File file = new File(finalPath);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
            T object = JSON.parseObject(jsonString, targetClass);
            return object;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
    }





}
