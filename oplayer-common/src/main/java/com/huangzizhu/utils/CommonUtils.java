package com.huangzizhu.utils;

import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.pojo.OperationLog;
import com.huangzizhu.pojo.playList.Playlist;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 对于通过多个代理的情况，第一个IP才是真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
    public static OperationLog getOperateLog(ProceedingJoinPoint joinPoint, Object result, long costTime, String ip, Integer operateId, Integer operateType) {
        OperationLog operateLog = new OperationLog();
        operateLog.setOperateId(operateId);// 设置操作人ID
        operateLog.setOperateType(operateType); // 设置操作人类型
        operateLog.setIp(ip); // 设置操作人IP
        operateLog.setOperateTime(LocalDateTime.now()); // 设置操作时间
        operateLog.setClassName(joinPoint.getTarget().getClass().getName()); // 设置类名
        operateLog.setMethodName(joinPoint.getSignature().getName()); // 设置方法名
        operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs())); // 设置方法参数
        operateLog.setReturnValue(result.toString()); // 设置返回值
        operateLog.setCostTime(costTime); // 设置耗时
        return operateLog;
    }
}
