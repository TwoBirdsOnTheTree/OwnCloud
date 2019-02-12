package com.mycloud.entity;

import com.mycloud.util.FileUtil;

import java.io.File;

/**
 * 文件类
 */
public class MyFile {
    // 文件名
    private String fileName;
    // 文件大小
    private long fileSize;
    // 文件路径
    // 是显示的文件路径, 去掉了${global.baseFilePath}
    private String filePath;

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
        return myFile;
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
}
