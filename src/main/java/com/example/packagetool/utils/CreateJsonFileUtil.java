package com.example.packagetool.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/7/25
 * Time: 14:27
 * Description:
 *
 * @author feng
 */
public class CreateJsonFileUtil {

    /**
     * 功能：
     * 传入一个对象，转成JSON字符串，然后格式化，输出在目标文件夹下
     * @param object 传入一个类对象
     * @param filePath 目标文件的位置
     * @param fileName 目标文件的名称
     */
    public static void createJsonFile(Object object, String filePath, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String string = mapper.writeValueAsString(object);
            System.out.println(string);
            JSONObject jsonObject = JSONObject.parseObject(string);
            String jsonString = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
            // 保证创建一个新文件
            // 如果父目录不存在，创建父目录
            // 拼接路径
            String fullPath = filePath + File.separator + fileName + ".json";
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // 如果已存在,删除旧文件
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
