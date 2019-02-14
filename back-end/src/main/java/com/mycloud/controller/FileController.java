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
                downloadFile(request, response, file);
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

    private void downloadFile(HttpServletRequest request, HttpServletResponse response, File downloadFile) throws Exception {
        //
        String contentType = request.getServletContext().getMimeType(downloadFile.getName());
        // 设置响应头
        // 参考资料：https://www.ibm.com/developerworks/cn/java/joy-down/index.html
        // 坑爹地方一：看代码
        response.setHeader("Accept-Ranges", "bytes");
        // 坑爹地方二：http状态码要为206
        response.setStatus(206);
        response.setContentType(contentType);
        response.setHeader("Content-Type", contentType);
        // 这里文件名换你想要的，inline表示浏览器直接实用（我方便测试用的）
        // 参考资料：http://hw1287789687.iteye.com/blog/2188500
        // response.setHeader("Content-Disposition", "inline;filename=test.mp3");
        // 断点续传
        // Range: bytes=40-100 ：第40个字节到第100个字节之间的数据
        String range = request.getHeader("Range");
        boolean rangeDownload = CommonUtil.isStringNotEmpty(range) && range.contains("bytes=");
        if (rangeDownload) {
            // range去掉头部的: bytes=
            range = range.substring("bytes=".length());
            // 获取开始/结束下载位置
            String[] ranges = range.split("-");
            String startByte = "0";
            String endByte = Long.valueOf(downloadFile.length()).toString();
            if (ranges.length == 1) {
                if (range.startsWith("-")) {
                    endByte = ranges[0];
                } else if (range.endsWith("-")) {
                    startByte = ranges[0];
                }
            } else if (ranges.length == 2) {
                startByte = ranges[0];
                endByte = ranges[1];
            }
            //要下载的长度 (为什么要+1?)
            long contentLength = Long.valueOf(endByte) - Long.valueOf(startByte) + 1;
            // 断点续传设置响应头
            response.setHeader("Content-Length", String.valueOf(contentLength));
            //坑爹地方三：Content-Range，格式为
            // [要下载的开始位置]-[结束位置]/[文件总大小]
            response.setHeader("Content-Range",
                    String.format("bytes %s-%s/%s", startByte, endByte, downloadFile.lastModified()));
        }
    }
}
