package com.example.packagetool.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.packagetool.dao.FileInfoMapper;
import com.example.packagetool.entity.FileInfo;
import com.example.packagetool.service.FileInfoService;
import com.example.packagetool.utils.ServiceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 10:56
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {


    private static final Logger logger = LogManager.getLogger(FileInfoServiceImpl.class);

    public final FileInfoMapper fileInfoMapper;

    public FileInfoServiceImpl(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }


    /**
     * 上传的文件以Uid的名称放在这里
     */
    @Value("${files.upload.path}")
    private String fileUploadPath;

    /**
     * 需要打包的时候，从数据库读出真实名称，输出到这个temp文件夹
     */
    @Value("${files.temp.path}")
    private String fileTempPath;


    /**
     * 打包的Zip输出在这里
     */
    @Value("${files.zip.path}")
    private String fileZipPath;

    @Override
    public FileInfo getFileInfoByUid(String uid) {
        // 根据uid，文件实际名称
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_uid",uid);
        List<FileInfo> fileInfoList = fileInfoMapper.selectList(queryWrapper);
        return fileInfoList.size() == 0 ? null : fileInfoList.get(0);
    }

    @Override
    public ServiceResult storeFile(MultipartFile[] files) {
        logger.info("");
        logger.info("扫描接收的文件数组信息：");
        logger.info(files);
        logger.info("接收文件数组：files_length = " + files.length);
        for (MultipartFile file: files) {
            logger.info("文件信息：");
            logger.info(file.getOriginalFilename());
            logger.info(file.getSize());
        }
        logger.info("扫描接收的文件数组信息完成：");
        logger.info("");
        // 1、拿到文件对象
        MultipartFile file = files[0];
        // file = "xxfw-xttz-xttz-java.zip"
        String originalFilename = file.getOriginalFilename();
        String contentType = FileUtil.extName(originalFilename);
        // 定义文件唯一标识码
        // fileUid = "xxxxxx.zip"
        String fileUid = IdUtil.fastSimpleUUID() + StrUtil.DOT + contentType;
        File uploadFile = new File(fileUploadPath + '/' + fileUid);
        // 需要转绝对路径
        File dest = uploadFile.getAbsoluteFile();
        // 判断文件存储路径是否存在，若无则新建路径
        File parentFile = uploadFile.getParentFile();
        logger.info("originalFileName: " + originalFilename);
        logger.info("contentType: " + contentType);
        logger.info("fileUid: " + fileUid);
        logger.info("uploadFile: " + uploadFile);
        logger.info("dest: " + dest);
        logger.info("parentFile: " + parentFile);
        if (!parentFile.exists()) {
            parentFile.mkdir();
            logger.info("当前文件存储路径：" + parentFile + "不存在，已经新建");
        }
        try {
            file.transferTo(dest);
            // 写一条数据库记录吧
            logger.info("写入一条文件记录，只记录oriName和Uid");
            FileInfo fileInfo = new FileInfo();
            // 	fastjson-1.2.58.tar
            fileInfo.setFileName(originalFilename);
            // 	a8a0402c03ba420f8d160f99ab14fd80.tar，返回给前端暂存和最后提交的是Uid
            fileInfo.setFileUid(fileUid);
            fileInfoMapper.insert(fileInfo);
            return ServiceResult.success("文件：" + originalFilename + "存储成功",fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResult.error("zip文件往存储位置写入失败",null);
        }



    }

    @Override
    public ServiceResult getOriNameByUid(String Uid) {
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
        fileInfoQueryWrapper.select().eq("FILE_UID",Uid);
        FileInfo fileInfo = fileInfoMapper.selectOne(fileInfoQueryWrapper);
        return ServiceResult.success("",fileInfo.getFileName());
    }

    /**
     * 因为挂载不需要给前端传名字之类的，感觉就把UID数组传回去就行了
     * @param files
     * @return
     */
    @Override
    public ServiceResult storePlugInFile(MultipartFile[] files) {
        ArrayList<String> plugInFileUids = new ArrayList<>();
        for (MultipartFile file: files) {
            // 1、拿到文件对象
            // file = "xxfw-xttz-xttz-java.zip"
            String originalFilename = file.getOriginalFilename();
            String contentType = FileUtil.extName(originalFilename);
            // 定义文件唯一标识码
            // fileUid = "xxxxxx.zip"
            String fileUid = IdUtil.fastSimpleUUID() + StrUtil.DOT + contentType;
            File uploadFile = new File(fileUploadPath + '/' + fileUid);
            // 需要转绝对路径
            File dest = uploadFile.getAbsoluteFile();
            // 判断文件存储路径是否存在，若无则新建路径
            File parentFile = uploadFile.getParentFile();
            logger.info("originalFileName: " + originalFilename);
            logger.info("contentType: " + contentType);
            logger.info("fileUid: " + fileUid);
            logger.info("uploadFile: " + uploadFile);
            logger.info("dest: " + dest);
            logger.info("parentFile: " + parentFile);
            if (!parentFile.exists()) {
                parentFile.mkdir();
                logger.info("当前文件存储路径：" + parentFile + "不存在，已经新建");
            }
            try {
                file.transferTo(dest);
                // 写一条数据库记录吧
                logger.info("写入一条文件记录，只记录oriName和Uid");
                FileInfo fileInfo = new FileInfo();
                // 	fastjson-1.2.58.tar
                fileInfo.setFileName(originalFilename);
                // 	a8a0402c03ba420f8d160f99ab14fd80.tar，返回给前端暂存和最后提交的是Uid
                fileInfo.setFileUid(fileUid);
                fileInfoMapper.insert(fileInfo);
                logger.info("文件：" + originalFilename + "存储成功");
                plugInFileUids.add(fileUid);
            } catch (IOException e) {
                e.printStackTrace();
                return ServiceResult.error("zip文件往存储位置写入失败",null);
            }
        }
        return ServiceResult.success("接收到 " + files.length + " 个挂载文件",plugInFileUids);
    }

    /**
     * 负责前端提交那些挂载文件的ID，然后把他们转运到temp，参与打包
     * 在打包的那个大方法里，调用这个
     * @param list
     * @return
     */
    @Override
    public ServiceResult transferPlugInFile(List<String> list,String fileTemp) {
        HashMap<File,File> fileHashMap = new HashMap<>();
        for (String uid:list) {
            ServiceResult oriNameByUid = getOriNameByUid(uid);
            String oriName = (String) oriNameByUid.getData();
            logger.info(oriNameByUid.getData());
            File uidFile = new File(fileUploadPath + "/" + uid).getAbsoluteFile();
            File oriFile = new File(fileTemp + "/" + oriName).getAbsoluteFile();
            fileHashMap.put(uidFile,oriFile);
        }
        for (File fileKey : fileHashMap.keySet()) {
            logger.info("renameTo方法");
            if (!fileHashMap.get(fileKey).getParentFile().exists()) {
                fileHashMap.get(fileKey).getParentFile().mkdirs();
            }
            fileKey.renameTo(fileHashMap.get(fileKey));
        }
        return ServiceResult.success();
    }

    @Override
    public ServiceResult clearAllFile() {
        File file = new File(fileUploadPath);
        FileUtil.del(file);
        File fileTemp = new File(fileTempPath);
        FileUtil.del(fileTemp);
        File fileZip = new File(fileZipPath);
        FileUtil.del(fileZip);
        return ServiceResult.success();
    }
}
