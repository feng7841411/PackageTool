package com.example.packagetool.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.example.packagetool.entity.BasicInfoForm;
import com.example.packagetool.entity.DeveloperInfoForm;
import com.example.packagetool.entity.ImageInfoForm;
import com.example.packagetool.entity.PackingToolInfo;
import com.example.packagetool.service.PackageToolService;
import com.example.packagetool.utils.*;
import com.example.packagetool.web.FileController;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 12:24
 * @Description:
 */
@Service
public class PackageToolServiceImpl implements PackageToolService {

    private static final Logger logger = LogManager.getLogger(PackageToolServiceImpl.class);
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



    private final FileInfoServiceImpl fileInfoService;

    public PackageToolServiceImpl(FileInfoServiceImpl fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @Override
    public ServiceResult packageZip(Map<String, Object> params) throws Exception {


        // 抽取参数
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        HashMap<String, Object> basicInfoFormMap = (HashMap<String, Object>) map.get("BasicInfoForm");
        HashMap<String, Object> imageInfoFormMap = (HashMap<String, Object>) map.get("ImageInfoForm");
        HashMap<String, Object> developerInfoFormMap = (HashMap<String, Object>) map.get("DeveloperInfoForm");
        List<String> pluginFileUIds = (List<String>) map.get("pluginFileUIds");

        // 2022年10月17日 12点27分 ，这3段代码，基本做的都是，前端参数，映射到JAVA后端的实体类
        // BasicInfoForm，从大Map里面拿参数，映射到我的实体类
        // 拼接版本号
        // 拼接唯一标识
        BasicInfoForm basicInfoForm = JsonUtils.mapToObj(basicInfoFormMap, BasicInfoForm.class);
        basicInfoForm.setSoftwareVersion(basicInfoForm.getMajorVersionNumber() + "." + basicInfoForm.getMinorVersionNumber() + "." + basicInfoForm.getRevisionNumber());
        basicInfoForm.setSoftwareUniqueName(basicInfoForm.getDomainName() + "-" + basicInfoForm.getSoftwareName() + "-" + basicInfoForm.getServiceName() + "-" + basicInfoForm.getLanguageName());
        // 2022年10月19日 16点36分 机动云要配置容器的Limit，那么打包就要先指定requests，这里把单位拼上
        basicInfoForm.setCpuRequests(basicInfoForm.getCpuRequests() + "核");
        basicInfoForm.setMemoryRequests(basicInfoForm.getMemoryRequests() + "Mi");


        // ImageInfoForm，从大Map里面拿参数，映射到我的实体类s
        // 取出原来的文件，放在预备的打包的地方
        ImageInfoForm imageInfoForm = JsonUtils.mapToObj(imageInfoFormMap, ImageInfoForm.class);


        // DeveloperInfoForm
        DeveloperInfoForm developerInfoForm = JsonUtils.mapToObj(developerInfoFormMap, DeveloperInfoForm.class);


        // 封装三部分信息，然后统一输出一个json文件
        PackingToolInfo packingToolInfo = new PackingToolInfo();
        packingToolInfo.setBasicInfoForm(basicInfoForm);
        // 这里ImageInfoForm前端把名字和Uid都存了，所以直接往里放
        packingToolInfo.setImageInfoForm(imageInfoForm);
        packingToolInfo.setDeveloperInfoForm(developerInfoForm);

        // 2022年11月1日 20点05分 修BUG，每次打包前，确认fileTemp里面没有其他文件
        // 2022年11月2日 08点34分 这样子也不对，既然是web，如果有多个人同时使用呢
        // 每次请求，独立一个临时待压缩区
        final String fileTempUid = IdUtil.fastSimpleUUID();
        File fileTemp = new File(fileTempPath + "/" + fileTempUid);
        File absoluteFile = fileTemp.getAbsoluteFile();
        if (!absoluteFile.exists()) {
            absoluteFile.mkdirs();
        } else {
            logger.warn("fileTemp文件夹存在，先清空");
            FileUtil.clean(fileTemp);
        }


        // 输出填写的描述信息的JSON文件，这个不进到数据库，可以直接输出到fileTemp
//        CreateJsonFileUtil.createJsonFile(packingToolInfo, fileTempPath, basicInfoForm.getSoftwareUniqueName());

        // 2022年10月18日 09点39分 根据安装部署的需求，把这个描述文件的JSON改固定名称
        CreateJsonFileUtil.createJsonFile(packingToolInfo, fileTemp.getAbsolutePath(), "desc");

        // 准备把镜像、2个yaml、file也放入fileTemp

        HashMap<File,File> fileHashMap = new HashMap<>();
        File imageKey = new File(fileUploadPath + "/" + imageInfoForm.getImageFileUid()).getAbsoluteFile();
        File imageValue = new File(fileTemp.getPath() + "/" + imageInfoForm.getImageFileName()).getAbsoluteFile();
        fileHashMap.put(imageKey,imageValue);

        File workLoadKey = new File(fileUploadPath + "/" + imageInfoForm.getWordLoadYamlFileUid()).getAbsoluteFile();
        File workLoadValue = new File(fileTemp.getPath() + "/" + imageInfoForm.getWorkLoadYamlFileName()).getAbsoluteFile();
        fileHashMap.put(workLoadKey,workLoadValue);

        File serviceKey = new File(fileUploadPath + "/" + imageInfoForm.getServiceYamlFileUid()).getAbsoluteFile();
        File serviceValue = new File(fileTemp.getPath() + "/" + imageInfoForm.getServiceYamlFileName()).getAbsoluteFile();
        fileHashMap.put(serviceKey,serviceValue);




        for (File fileKey : fileHashMap.keySet()) {
            // renameTo是重命名移动，源文件会消失
            fileKey.renameTo(fileHashMap.get(fileKey));
        }

        // 2022年10月27日 09点41分
        fileInfoService.transferPlugInFile(pluginFileUIds,fileTemp.getPath());

//        // 获取fileTemp文件夹下的所有文件File类
//        File[] filesAll = fileTemp.listFiles();
//        // 目标ZIP文件
//        File zipFile = new File(fileZipPath);
//        if (!zipFile.getAbsoluteFile().exists()) {
//            zipFile.getAbsoluteFile().mkdirs();
//        }
        // 删掉之前的ZIP ，这个是不对的，多个用户交错，不能删别人的包
//        final File file1 = new File(fileZipPath);
//        if (file1.exists()) {
//            FileUtil.del(file1);
//        }else {
//            FileUtil.mkdir(file1);
//        }

        File targetZipFile = new File(fileZipPath + "/" + basicInfoForm.getSoftwareUniqueName() + ".zip").getAbsoluteFile();
//        File parentFile = targetZipFile.getParentFile();
//        if (!parentFile.exists()) {
//            parentFile.mkdir();
//        }
//        ZipUtil.zipMultipleFiles(targetZipFile.toString(), filesAll);
//        // 将目标路径下所有文件封装成ZIP
//        for (File file : filesAll) {
//            file.delete();
//        }
        final File file = new File(fileTemp.getAbsoluteFile().getPath());

        final List<File> files = FileUtil.loopFiles(file);
        logger.info("所有文件准备完成，即将封装ZIP，现在打印所有准备封装的文件名：");
        for (File f:files) {
            logger.info(f.getPath());
        }
        logger.info("文件信息打印结束，zipUtil启动");
        ZipUtil.zip(fileTemp.getAbsoluteFile().getPath(),targetZipFile.getPath());

        logger.info("zipUtil调用结束，返回压缩包信息");
        logger.info("封装完成，再次清空fileTemp");
        // 每次请求，UID的临时文件夹删除
        FileUtil.del(fileTemp);
        // 上传文件缓冲区也删除，但是不能删别人的，只能删除自己上传的，暂时不删吧
//        FileUtil.clean(fileUploadPath);
        String zipName = basicInfoForm.getSoftwareUniqueName() + ".zip";
        return ServiceResult.success("200", zipName);


    }
}
