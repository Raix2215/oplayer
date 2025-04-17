package com.huangzizhu.pojo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file.watcher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileWatcherProperties {
    private long pollInterval = 1000; // 默认值为1秒
    private String directory;
}