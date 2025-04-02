package com.huangzizhu.app;

import com.huangzizhu.exception.InitException;
import com.huangzizhu.pojo.MusicFileFeature;
import com.huangzizhu.service.SongService;
import com.huangzizhu.utils.MusicFeatureReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;

@Slf4j
@Component
public class MusicDataSyncDiffChecker implements Runnable{

    private HashSet<MusicFileFeature> localMusic;
    private HashSet<String> DBMusicMD5;

    @Autowired
    private SongService songService;
    @Autowired
    private MusicFeatureReader musicFeatureReader;

    public void start() {
        log.info("开始进行数据差异化比较");
        getDBData();
        getLocalData();
        //先用本地数据作为标准，找出数据库的不同
        DBMusicMD5.stream().filter(md5 -> !localMusic.contains(md5)).forEach(System.out::println);
        //在对比数据库进行删除
        localMusic.stream().filter(md5 -> !DBMusicMD5.contains(md5)).forEach(System.out::println);
        log.info("数据差异化比较完成");
    }


    private void getDBData() {
        DBMusicMD5 = songService.getAllMD5();
    }

    private void getLocalData() {
        try {
            localMusic = musicFeatureReader.getLocalMusicData();
        } catch (IOException e) {
            throw new InitExcept    ion("初始化出错：数据差异化比较出错：读取文件发生错误", e);
        }
    }

    @Override
    public void run() {
        start();
    }
}
