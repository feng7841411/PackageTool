package com.example.packagetool.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author feng
 * @date 2022/8/8
 * @time 23:03
 * @apiNote
 */
public class ZipUtil {
    public static void zipMultipleFiles(String zipFileName, File[] files) throws IOException {
        ZipOutputStream zipOutputStream = null;
        try {
            // 输出流
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            // 遍历每一个文件，进行输出
            for (File file : files) {
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream fileInputStream = new FileInputStream(file);
                int readLen;
                byte[] buffer = new byte[1024];
                while ((readLen = fileInputStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, readLen);
                }
                // 关闭流
                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
        } finally {
            if (null != zipOutputStream) {
                try {
                    zipOutputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}
