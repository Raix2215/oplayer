package com.huangzizhu.utils;

import com.huangzizhu.exception.MusicParseException;
import com.huangzizhu.pojo.config.FileWatcherProperties;
import com.huangzizhu.pojo.music.MusicFileFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

@Component
public class MusicFeatureReader {

    @Autowired
    private FileWatcherProperties fileWatcherProperties;
    private HashSet<MusicFileFeature> localMusic = new HashSet<>();

    public HashSet<MusicFileFeature> getLocalMusicData() throws IOException {
        Path folder = Paths.get(System.getProperty("user.dir"), fileWatcherProperties.getDirectory());
        // 遍历文件夹
        Files.walk(folder)
                .filter(Files::isRegularFile) // 确保是文件
                .filter(path -> CommonUtils.isSupportedFormat(path.toString())) // 过滤文件
                .forEach(path -> {
                    try {
                        addFileData(path);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });
        return localMusic;
    }
    public MusicFileFeature getMusicFileFeature(File file) {
        // 获取文件名和路径
        String fileName = file.getName();
        if(CommonUtils.isSupportedFormat(fileName)) {
            // 过滤.mp3和.wav文件
            String absolutePath = file.getAbsolutePath();
            // 计算MD5
            String md5 = null;
            try {
                md5 = calculateMD5(file);
            } catch (Exception e) {
                throw new MusicParseException("文件解析失败",file, e);
            }
            return new MusicFileFeature(fileName, absolutePath, md5);
        } else {
            return null;
        }
    }

    private void addFileData(Path path) throws NoSuchAlgorithmException, IOException {
        // 获取文件名和路径
        String fileName = path.getFileName().toString();
        String filePath = path.toAbsolutePath().toString();
        // 计算MD5
        String md5 = calculateMD5(path.toFile());
        // 创建MusicFileFeature对象并加入HashSet
        localMusic.add(new MusicFileFeature(fileName, filePath, md5));
    }

    // 计算文件的MD5值
    public static String calculateMD5(File file) throws NoSuchAlgorithmException, IOException {
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