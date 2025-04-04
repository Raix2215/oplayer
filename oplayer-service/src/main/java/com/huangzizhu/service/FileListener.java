package com.huangzizhu.utils;

import com.huangzizhu.pojo.MusicFileFeature;
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
        log.info("文件新增: " + file.getAbsolutePath());
        MusicFileFeature musicFileFeature = musicFeatureReader.getMusicFileFeature(file);
        if(musicFileFeature != null) {
            newMusicSubmitter.submitMusic(musicFileFeature);
        }
    }

    @Override
    public void onFileDelete(File file) {
        log.info("文件删除: " + file.getAbsolutePath());
        if (CommonUtils.isSupportedFormat(file))
        {
            //只能尝试文件名中是否存在md5是否存在于数据库
            String filename = CommonUtils.getFileNameWithoutExtension(file);
            invalidMusicMarker.submitMD5(filename);
        }
    }

    @Override
    public void onFileChange(File file) {
        log.info("文件修改: " + file.getAbsolutePath());
    }
}