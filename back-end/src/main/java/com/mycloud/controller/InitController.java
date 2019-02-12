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
        // 在项目根文件夹下创建配置文件夹
        // 1. 首先需要判断是否已经创建了该文件夹
        // 2. 这个创建的文件夹不应该在页面中显示

        // 在配置文件夹下创建db文件

        // 获取sql文件, 执行sql
    }

}
