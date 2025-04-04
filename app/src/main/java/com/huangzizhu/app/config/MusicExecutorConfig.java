package com.huangzizhu.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class MusicExecutorConfig {
    @Bean(name = "MusicOperationExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // 核心线程数
        executor.setMaxPoolSize(10); // 最大线程数\
        executor.setQueueCapacity(100); // 任务队列容量
        executor.setThreadNamePrefix("MusicOperationExecutorThread-");
        executor.initialize();
        return executor;
    }
}