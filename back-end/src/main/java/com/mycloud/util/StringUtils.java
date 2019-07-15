package com.mycloud.util;

/**
 * String
 */
public class StringUtils {
    /**
     * 是否为空, 未trim
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isStringEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isStringNotEmpty(String str) {
        return !isStringEmpty(str);
    }
}
