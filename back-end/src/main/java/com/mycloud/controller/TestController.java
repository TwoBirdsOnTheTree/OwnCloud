package com.mycloud.controller;

import com.mycloud.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    /**
     * 测试
     */
    @RequestMapping("/*")
    public String test() {
        return FileUtil.getFullFilePath("");
    }

}
