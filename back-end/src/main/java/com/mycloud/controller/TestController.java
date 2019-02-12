package com.mycloud.controller;

import com.mycloud.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * for test controller
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    /**
     * for test
     *
     * @return string
     */
    @RequestMapping("/*")
    public String test() {
        return FileUtil.getFullFilePath("");
    }

}
