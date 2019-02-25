package com.mycloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * 系统初始化Controller
 */
@RestController
@RequestMapping("/init")
public class InitController extends BaseController {

    public void ss() {
        try {
            // 启动启动时判断是否已经初始化过
            // 如果没有初始化过, 则所以跳转一律跳到初始化页面(拦截器)
            // 并且将是否已经初始化缓存, 方便判断

            // 在项目根文件夹下创建配置文件夹
            // 1. 首先需要判断是否已经创建了该文件夹
            // 2. 这个创建的文件夹不应该在页面中显示

            // 在配置文件夹下创建db文件
            File dbFile = new File("");
            boolean dbFileExists = dbFile.exists();
            if (dbFileExists) {
                ;
            } else {
                //
                // 创建父文件夹
                File parentFile = dbFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                // 创建db文件
                dbFile.createNewFile();
            }

            // 创建数据库连接

            // 未初始化过, 则获取sql文件, 执行sql
            if (true) {
                String sqlFileContent = getSQLFileContent();
            }

            // 读取数据库, 将一些数据缓存, 提高访问速度, 同时注意更新数据库时要同步

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取SQL文件内容
     *
     * @return sql文件内容
     * @throws IOException io异常
     */
    private String getSQLFileContent() throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = InitController.class.getResourceAsStream("/ownCloudDb.sql");
            StringBuffer sqlFileStringBuffer = new StringBuffer();
            byte[] bytes = new byte[512];
            for (int readBytesLength = 0; (readBytesLength = inputStream.read(bytes)) > 0; ) {
                sqlFileStringBuffer.append(new String(bytes, 0, readBytesLength));
            }
            return sqlFileStringBuffer.toString();
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
    }

}
