package com.mycloud.controller;

import com.alibaba.fastjson.JSON;
import com.mycloud.util.CommonUtil;
import com.mycloud.util.FileUtil;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 文件Controller
 */
@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 显示本文件夹下文件列表, 不递归
     */
    @RequestMapping("/fileList")
    public String fileList(HttpServletRequest request) {
        String filePath = request.getParameter("filePath");
        return JSON.toJSONString(FileUtil.getFolderFileList(filePath));
    }

    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getParameter("filePath");
            String fullPath = FileUtil.getFullFilePath(filePath);
            File file = new File(fullPath);
            if (!file.exists()) {
                return;
            }
            // 判断是文件下载还是文件夹下载
            if (file.isFile()) {
                downloadFile(request, response);
            } else if (file.isDirectory()) {

            }
        } catch (ClientAbortException clientAbortException) {
            System.out.println("下载取消: ");
            clientAbortException.printStackTrace();
        } catch (Exception e) {
            System.out.println("下载异常: ");
            e.printStackTrace();
        }
    }

    private void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        // 断点续传
        // Range: bytes=40-100 ：第40个字节到第100个字节之间的数据
        String range = request.getHeader("Range");
        if (CommonUtil.isStringNotEmpty(range)) {
            // 获取开始/结束下载位置
            String[] ranges = range.split("-");
            String startByte = null;
            String endByte = null;
            if (ranges.length == 1) {
                if (range.startsWith("-")) {
                    endByte = ranges[1];
                } else if (range.endsWith("-")) {
                    startByte = ranges[0];
                }
            } else if (ranges.length == 2) {

            }
        }
    }
}
