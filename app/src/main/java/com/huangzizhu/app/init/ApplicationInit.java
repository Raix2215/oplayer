package com.huangzizhu.app.init;

import com.huangzizhu.service.musicManagement.InvalidMusicMarker;
import com.huangzizhu.service.musicManagement.MusicDataSyncDiffChecker;
import com.huangzizhu.service.musicManagement.NewMusicSubmitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ApplicationInit {
    @Autowired
    private FileAlterationMonitor fileAlterationMonitor;
    @Autowired
    private MusicDataSyncDiffChecker musicDataSyncDiffChecker;
    @Autowired
    private NewMusicSubmitter newMusicSubmitter;
    @Autowired
    private InvalidMusicMarker invalidMusicMarker;
    @Autowired
    @Qualifier("MusicOperationExecutor")
    private Executor executor;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws Exception {
        log.info("应用启动完成，执行初始化操作...");
        File musicFolder = new File("music");
        if (!musicFolder.exists()) {
            musicFolder.mkdirs();
        }

        // 初始执行一次
        executor.execute(musicDataSyncDiffChecker);

        // 设置定时任务，每小时执行一次
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> executor.execute(musicDataSyncDiffChecker),
                1, 1, TimeUnit.HOURS);

        // 文件监听器也使用线程池执行
        executor.execute(() -> {
            try {
                fileAlterationMonitor.start();
            } catch (Exception e) {
                log.error("文件监听器启动失败", e);
            }
        });

        newMusicSubmitter.start();
        invalidMusicMarker.start();
    }
}