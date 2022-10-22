package com.example.packagetool.web;

import com.example.packagetool.service.impl.PackageToolServiceImpl;
import com.example.packagetool.utils.Result;
import com.example.packagetool.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 12:17
 * @Description:
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/packingToolForm")
public class PackageInfoController {

    @Autowired
    private PackageToolServiceImpl packageToolService;


    /**
     * 打包的Zip输出在这里
     */
    @Value("${files.zip.path}")
    private String fileZipPath;


    @PostMapping("/postPackingToolInfo")
    public Result postPackingToolInfo(@RequestBody Map<String, Object> params) throws Exception {
        ServiceResult serviceResult = packageToolService.packageZip(params);
        String zipFileName = (String) serviceResult.getData();

        return Result.success("封装完成，可下载",zipFileName);
    }

    @GetMapping("/getZipFile")
    public void download(@RequestParam("zipFileName") String zipFileName,
                         HttpServletResponse response) throws Exception {
        File file = new File(fileZipPath + "/" + zipFileName).getAbsoluteFile();
        System.out.println(file.toString());
        if (!file.exists()) {

        } else {
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        }

    }



}
