package com.huangzizhu.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class ApplicationInit {
    @Autowired
    private FileAlterationMonitor fileAlterationMonitor;
    @Autowired
    private MusicDataSyncDiffChecker musicDataSyncDiffChecker;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws Exception {
        log.info("应用启动完成，执行初始化操作...");
        File musicFolder = new File("music");
        if (!musicFolder.exists()) {
            musicFolder.mkdirs();
        }
        new Thread(musicDataSyncDiffChecker).start();
        fileAlterationMonitor.start();
    }
}