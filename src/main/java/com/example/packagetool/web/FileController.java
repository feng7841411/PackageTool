package com.example.packagetool.web;

import com.example.packagetool.dao.FileInfoMapper;
import com.example.packagetool.service.impl.FileInfoServiceImpl;
import com.example.packagetool.utils.Result;
import com.example.packagetool.utils.ServiceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 11:17
 * @Description:
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/file")
public class FileController {



    private static final Logger logger = LogManager.getLogger(FileController.class);



    private final FileInfoServiceImpl fileInfoService;

    public FileController(FileInfoServiceImpl fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam("files") MultipartFile[] files){
        ServiceResult serviceResult = fileInfoService.storeFile(files);
        logger.info(serviceResult.getMsg());
        logger.info(serviceResult.getData());
        // 返回的是 6d0e9a4f64084bcd801fda8381eecd11.tar
        return Result.success("",serviceResult.getData());
    }


    @PostMapping("/uploadWordLoadYaml")
    public Result uploadWordLoadYaml(@RequestParam("files") MultipartFile[] files){
        ServiceResult serviceResult = fileInfoService.storeFile(files);
        logger.info(serviceResult.getMsg());
        logger.info(serviceResult.getData());
        // 返回的是 6d0e9a4f64084bcd801fda8381eecd11.yaml
        return Result.success("",serviceResult.getData());
    }


    @PostMapping("/uploadServiceYaml")
    public Result uploadServiceYaml(@RequestParam("files") MultipartFile[] files){
        ServiceResult serviceResult = fileInfoService.storeFile(files);
        logger.info(serviceResult.getMsg());
        logger.info(serviceResult.getData());
        // 返回的是 6d0e9a4f64084bcd801fda8381eecd11.yaml
        return Result.success("",serviceResult.getData());
    }



}
