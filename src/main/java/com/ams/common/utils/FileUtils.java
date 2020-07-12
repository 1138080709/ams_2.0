package com.ams.common.utils;

/**
 * @Author: WuWeiquan
 * @Date: 2020/6/14 14:50
 */
public class FileUtils {

    /**
     * 返回文件后缀名(不带.)
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName) {
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index+1);
    }
}
