package com.example.packagetool.web;

import com.example.packagetool.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 冯金河
 * @Date: 2022/11/3 18:35
 * @Description:
 */

@RestController
public class TestController {

    @PostMapping("/testAxiosApi")
    public Result testAxiosApi() {
        return Result.success("后端收到啦","");
    }

    @PostMapping("/v1/testAxiosApi")
    public Result testAxiosApi1() {
        return Result.success("后端收到啦1","");
    }

}
