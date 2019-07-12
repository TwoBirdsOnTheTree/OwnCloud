package com.mycloud.util;

import com.mycloud.entity.MyFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件工具类
 */
@SuppressWarnings("unchecked")
@Component
public class FileUtil {
    //
    public static String baseFilePath;

    /**
     * 返回文件的完整路径
     *
     * @param filePath 文件路径
     * @return 完整路径
     */
    public static String getFullFilePath(String filePath) {
        if (null == filePath) {
            filePath = "";
        }
        // 去除filePath开始的文件分隔符
        if (filePath.startsWith(File.separator)) {
            filePath = filePath.substring(1);
        }
        return baseFilePath + filePath;
    }

    public static List<MyFile> getFolderFileList(String filePath) {
        String fullFilePath = getFullFilePath(filePath);
        File file = new File(fullFilePath);
        if (!file.exists() || file.isFile()) {
            return Collections.EMPTY_LIST;
        }
        File[] subFiles = file.listFiles();
        if (subFiles == null) {
            return Collections.EMPTY_LIST;
        }
        return toMyFiles(Arrays.asList(subFiles));
    }

    private static List<MyFile> toMyFiles(List<File> files) {
        if (null == files) {
            return Collections.EMPTY_LIST;
        }
        return files.parallelStream()
                .map(MyFile::toMyFile)
                .collect(Collectors.toList());
    }

    @Value("${global.baseFilePath}")
    public void setBaseFilePath(String baseFilePath) {
        // base文件路径末尾, 始终添加文件分隔符
        if (!baseFilePath.endsWith(File.separator)) {
            baseFilePath = baseFilePath + File.separator;
        }
        FileUtil.baseFilePath = baseFilePath;
    }
}

