package com.mycloud.entity;

import com.alibaba.fastjson.JSON;
import com.mycloud.util.FileUtil;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 文件类
 */
public class MyFile {
    // 文件名
    private String fileName;
    // 文件大小
    private long fileSize;
    // 文件路径
    // 是显示的文件路径, 即去掉了${global.baseFilePath}
    private String filePath;
    // 修改时间
    private LocalDateTime modifiedTime;
    // 是否是文件夹
    private boolean ifDirectory;

    /**
     * 将 java.io.File 转换为 MyFile
     *
     * @param file 文件类
     * @return MyFile类
     */
    public static MyFile toMyFile(File file) {
        MyFile myFile = new MyFile();
        myFile.setFileName(file.getName());
        myFile.setFileSize(file.getTotalSpace());
        String filePath = file.getAbsolutePath();
        filePath = filePath.replace(FileUtil.baseFilePath, "");
        if (!filePath.startsWith(File.separator)) {
            filePath = File.separator + filePath;
        }
        myFile.setFilePath(filePath);
        Instant instant = new Date(file.lastModified()).toInstant();
        myFile.setModifiedTime(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        myFile.setIfDirectory(file.isDirectory());
        return myFile;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public boolean isIfDirectory() {
        return ifDirectory;
    }

    public void setIfDirectory(boolean ifDirectory) {
        this.ifDirectory = ifDirectory;
    }
}
