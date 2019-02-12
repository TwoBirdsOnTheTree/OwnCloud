package com.mycloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统初始化Controller
 */
@RestController
@RequestMapping("/init")
public class InitController extends BaseController {

    public void ss() {
        // 执行db文件
    }

}
