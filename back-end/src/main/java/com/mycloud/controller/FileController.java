package com.mycloud.controller;

import com.alibaba.fastjson.JSON;
import com.mycloud.util.CommonUtil;
import com.mycloud.util.FileUtil;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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

    /**
     * 下载文件或文件夹
     *
     * @param request  request
     * @param response response
     */
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
                // 打包
                // archiveFile();
                File archiveFile = null;
                downloadFile(request, response, archiveFile);
            }
        } catch (Exception e) {
            System.out.println("下载异常: ");
            e.printStackTrace();
        }
    }

    private void downloadFile(HttpServletRequest request, HttpServletResponse response, File downloadFile) throws Exception {
        // 文件目录
        File music = downloadFile;
        System.out.println("文件是否可读: " + music.canRead());
        String range = request.getHeader("Range");

        // 开始下载位置
        long startByte = 0;
        // 结束下载位置
        long endByte = music.length() - 1;

        boolean rangeBoolean = range != null && range.contains("bytes=") && range.contains("-");
        // 有range的话
        if (rangeBoolean) {
            range = range.substring(range.lastIndexOf("=") + 1).trim();
            String[] ranges = range.split("-");
            try {
                //判断range的类型
                if (ranges.length == 1) {
                    //类型一：bytes=-2343
                    if (range.startsWith("-")) {
                        endByte = Long.parseLong(ranges[0]);
                    }
                    //类型二：bytes=2343-
                    else if (range.endsWith("-")) {
                        startByte = Long.parseLong(ranges[0]);
                    }
                }
                //类型三：bytes=22-2343
                else if (ranges.length == 2) {
                    startByte = Long.parseLong(ranges[0]);
                    endByte = Long.parseLong(ranges[1]);
                }

            } catch (NumberFormatException e) {
                startByte = 0;
                endByte = music.length() - 1;
            }
        }

        // 要下载的长度（为啥要加一问小学数学老师去）
        long contentLength = endByte - startByte + 1;
        // 文件名
        String fileName = music.getName();
        // 文件类型
        String contentType = request.getServletContext().getMimeType(fileName);

        // 各种响应头设置
        // 参考资料：https://www.ibm.com/developerworks/cn/java/joy-down/index.html
        // 坑爹地方一：看代码
        if (rangeBoolean)
            response.setHeader("Accept-Ranges", "bytes");
        // 坑爹地方二：http状态码要为206
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setContentType(contentType);
        response.setHeader("Content-Type", contentType);
        // 这里文件名换你想要的，inline表示浏览器直接实用（我方便测试用的）
        // 参考资料：http://hw1287789687.iteye.com/blog/2188500
        response.setHeader("Content-Disposition", "attachment;filename=\"Untitled-5.txt\"");
        response.setHeader("Content-Length", String.valueOf(contentLength));
        // 坑爹地方三：Content-Range，格式为
        // [要下载的开始位置]-[结束位置]/[文件总大小]
        if (rangeBoolean)
            response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + music.length());
        // 下载
        BufferedOutputStream outputStream = null;
        RandomAccessFile randomAccessFile = null;
        // 已传送数据大小
        long transmitted = 0;
        try {
            randomAccessFile = new RandomAccessFile(music, "r");
            outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[4096];
            int len = 0;
            randomAccessFile.seek(startByte);
            // 坑爹地方四：判断是否到了最后不足4096（buff的length）个byte这个逻辑（(transmitted + len) <= contentLength）要放前面！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
            // 不然会会先读取randomAccessFile，造成后面读取位置出错，找了一天才发现问题所在
            while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
                transmitted += len;
                //TODO 停一下，方便测试，用的时候删了就行了
                Thread.sleep(10);
            }
            // 处理不足buff.length部分
            if (transmitted < contentLength) {
                len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                outputStream.write(buff, 0, len);
                transmitted += len;
            }
            //
            outputStream.flush();
            response.flushBuffer();
            randomAccessFile.close();
            System.out.println("下载完毕：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (ClientAbortException e) {
            // 捕获此异常表示用户停止下载
            System.out.println("用户停止下载：" + startByte + "-" + endByte + "：" + transmitted);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("下载IOException: ");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("下载InterruptedException: ");
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
