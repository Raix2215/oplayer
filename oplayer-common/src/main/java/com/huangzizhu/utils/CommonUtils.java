package com.huangzizhu.utils;

import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.pojo.playList.Playlist;

import java.io.File;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final Pattern MD5_PATTERN = Pattern.compile("^[0-9a-fA-F]{32}$");
    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName(); // 获取完整文件名
        int dotIndex = fileName.lastIndexOf('.'); // 找到最后一个点的位置
        if (dotIndex > 0) { // 确保文件名中包含点，并且点不是第一个字符
            return fileName.substring(0, dotIndex); // 返回点之前的部分
        } else {
            return fileName; // 如果没有点，返回整个文件名
        }
    }
    public static Boolean isSupportedFormat(File file){
        String filename = file.getName();
        return filename.endsWith(".mp3") || filename.endsWith(".wav") || filename.endsWith(".flac");
    }
    public static Boolean isSupportedFormat(String path){
        return path.endsWith(".mp3") || path.endsWith(".wav") || path.endsWith(".flac");
    }
    public static Boolean isMd5edFileName(File file){
        return isMD5(getFileNameWithoutExtension(file));
    }
    public static Boolean isMd5edFileName(String path){
        return isMd5edFileName(new File(path));
    }
    public static Integer max(Integer a, Integer b){return a > b ? a : b;}

    /**
     * 检测字符串是否可能是MD5哈希值
     * @param input 待检测的字符串
     * @return 如果是MD5哈希值返回true，否则返回false
     */
    public static boolean isMD5(String input) {
        return MD5_PATTERN.matcher(input).matches();
    }
    public static boolean isBlank(String str) {return str == null || str.isBlank();}
}
