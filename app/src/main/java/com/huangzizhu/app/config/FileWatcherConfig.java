package com.huangzizhu.app.config;

import com.huangzizhu.service.musicManagement.FileListener;
import com.huangzizhu.pojo.config.FileWatcherProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class FileWatcherConfig {
    @Autowired
    private FileListener fileListener;
    @Autowired
    private FileWatcherProperties fileWatcherProperties;

    @Bean
    public FileAlterationMonitor fileAlterationMonitor() {
        log.info("开始文件监听：{}，轮询间隔{}", fileWatcherProperties.getDirectory(), fileWatcherProperties.getPollInterval());
        long interval = TimeUnit.SECONDS.toMillis(fileWatcherProperties.getPollInterval());// 轮询间隔为 1 秒
        log.info(new File(System.getProperty("user.dir"),fileWatcherProperties.getDirectory()).getAbsolutePath());
        FileAlterationObserver observer = new FileAlterationObserver(new File(System.getProperty("user.dir"),fileWatcherProperties.getDirectory()));
        observer.addListener(fileListener);
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        return monitor;
    }
}