package com.huangzizhu.pojo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FileListener extends FileAlterationListenerAdaptor {
    @Override
    public void onFileCreate(File file) {
        log.info("文件创建: " + file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file) {
        log.info("文件删除: " + file.getAbsolutePath());
    }

    @Override
    public void onFileChange(File file) {
        log.info("文件修改: " + file.getAbsolutePath());
    }
}