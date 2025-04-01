package com.huangzizhu.utils;

import com.huangzizhu.pojo.FileWatcherProperties;
import com.huangzizhu.pojo.MusicFileFeature;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class MusicFeatureReader {

    @Autowired
    private FileWatcherProperties fileWatcherProperties;
    private Set<MusicFileFeature> localMusic = new HashSet<>();
    private Path folder = Paths.get(System.getProperty("user.dir"), fileWatcherProperties.getDirectory());

    public  void T(String[] args) throws IOException {
        // 指定要扫描的文件夹路径
        // 遍历文件夹
        Files.walk(folder)
                .filter(Files::isRegularFile) // 确保是文件
                .filter(path -> path.toString().endsWith(".mp3") || path.toString().endsWith(".wav")) // 过滤.mp3和.wav文件
                .forEach(path -> {
                    try {
                        // 获取文件名和路径
                        String fileName = path.getFileName().toString();
                        String filePath = path.toAbsolutePath().toString();
                        // 计算MD5
                        String md5 = calculateMD5(path.toFile());
                        // 创建MusicFileFeature对象并加入HashSet
                        localMusic.add(new MusicFileFeature(fileName, filePath, md5));
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });


    }

    // 计算文件的MD5值
    private static String calculateMD5(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}