package com.huangzizhu.service;

import com.huangzizhu.pojo.MusicFileFeature;
import com.huangzizhu.utils.CommonUtils;
import com.huangzizhu.utils.MusicFeatureReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FileListener extends FileAlterationListenerAdaptor {
    @Autowired
    private MusicFeatureReader musicFeatureReader;
    @Autowired NewMusicSubmitter newMusicSubmitter;
    @Autowired InvalidMusicMarker invalidMusicMarker;

    @Override
    public void onFileCreate(File file) {
        if (paused) return;
        log.info("文件新增: " + file.getAbsolutePath());
        // 已经md5过的文件不需要再处理
        if(CommonUtils.isMd5edFileName(file)) return;
        // MusicFileFeature会进行文件格式判断
        MusicFileFeature musicFileFeature = musicFeatureReader.getMusicFileFeature(file);
        if(musicFileFeature != null) {
            newMusicSubmitter.submitMusic(musicFileFeature);
        }
    }

    @Override
    public void onFileDelete(File file) {
        if (paused) return;
        log.info("文件删除: " + file.getAbsolutePath());
        //只对文件名是md5进行删除操作且支持的文件
        if(CommonUtils.isMd5edFileName(file) && CommonUtils.isSupportedFormat(file))
        {
            String filename = CommonUtils.getFileNameWithoutExtension(file);
            invalidMusicMarker.submitMD5(filename);
        }
    }

    @Override
    public void onFileChange(File file) {
        if (paused) return;
        log.info("文件修改: " + file.getAbsolutePath());
    }
    // 添加一个标志位
    private volatile boolean paused = false;

    // 添加暂停和恢复方法
    public void pause() {
        log.info("文件监听器暂停");
        this.paused = true;
    }

    public void resume() {
        log.info("文件监听器恢复");
        this.paused = false;
    }
}