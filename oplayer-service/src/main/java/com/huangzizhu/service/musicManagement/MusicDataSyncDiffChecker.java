package com.huangzizhu.service;

import com.huangzizhu.exception.InitException;
import com.huangzizhu.pojo.MusicFileFeature;
import com.huangzizhu.utils.MusicFeatureReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MusicDataSyncDiffChecker implements Runnable {

    private HashSet<MusicFileFeature> localMusic;
    private HashSet<String> DBMusicMD5;

    @Autowired
    private SongService songService;
    @Autowired
    private MusicFeatureReader musicFeatureReader;
    @Autowired
    private NewMusicSubmitter newMusicSubmitter;
    @Autowired
    private InvalidMusicMarker invalidMusicMarker;
    @Autowired
    private FileListener fileListener;

    public void start() throws Exception {
        try {
            log.info("开始进行数据差异化比较");
            // 暂停文件监听
            fileListener.pause();
            startTask();
            log.info("数据差异化比较完成");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 恢复文件监听
            fileListener.resume();
        }
    }

    private void startTask() {
        getLocalData();
        getDBData();

        // 1. 找出数据库中有但本地不存在的音乐（标记为无效）
        Set<String> localMD5s = localMusic.stream()
                .map(MusicFileFeature::getMd5)
                .collect(Collectors.toSet());

        DBMusicMD5.stream()
                .filter(md5 -> !localMD5s.contains(md5))
                .forEach(invalidMusicMarker::submitMD5);

        localMusic.stream()
                .filter(md5 -> !DBMusicMD5.contains(md5.getMd5()))
                .forEach(musicFileFeature -> newMusicSubmitter.submitMusic(musicFileFeature));
    }

    private void getDBData() {
        DBMusicMD5 = songService.getAllMD5();
    }

    private void getLocalData() {
        try {
            localMusic = musicFeatureReader.getLocalMusicData();
        } catch (IOException e) {
            throw new InitException("初始化出错：数据差异化比较出错：读取文件发生错误", e);
        }
    }

    @Override
    public void run() {
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}