package com.example.packagetool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.packagetool.entity.FileInfo;
import com.example.packagetool.utils.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 10:55
 * @Description:
 */
public interface FileInfoService extends IService<FileInfo> {
    public FileInfo getFileInfoByUid(String uid);

    public ServiceResult storeFile(MultipartFile[] files);

    public ServiceResult getOriNameByUid(String Uid);


    public ServiceResult storePlugInFile(MultipartFile[] files);



    public ServiceResult transferPlugInFile(List<String> list,String fileTemp);

    public ServiceResult clearAllFile();

}
