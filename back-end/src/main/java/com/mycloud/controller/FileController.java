package com.mycloud.controller;

import com.alibaba.fastjson.JSON;
import com.mycloud.util.FileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件Controller
 */
@RestController
@RequestMapping("file")
public class FileController {
    /**
     * 显示本文件夹下文件列表, 不递归
     *
     * @return 文件列表
     */
    @RequestMapping("/fileList")
    public String fileList(HttpServletRequest request) {
        String filePath = request.getParameter("filePath");
        return JSON.toJSONString(FileUtil.getFolderFileList(filePath));
    }
}
