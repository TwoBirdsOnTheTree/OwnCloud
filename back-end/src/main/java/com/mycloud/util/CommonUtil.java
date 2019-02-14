package com.mycloud.util;

/**
 * 通用的工具类
 */
public class CommonUtil {

    /**
     * 判断字符串是否不为空
     *
     * @param string 字符串
     * @return 是否不为空
     */
    public static boolean isStringNotEmpty(String string) {
        if (null == string) {
            return false;
        }
        return string.trim().length() > 0;
    }
}
