package com.huangzizhu.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableScheduling
@Configuration
public class MusicExecutorConfig {
    @Bean(name = "MusicOperationExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);                 // 核心线程数 = 常驻线程（2） + 1（缓冲）
        executor.setMaxPoolSize(6);                  // 最大线程数 = 核心线程数 × 2（应对每小时检查）
        executor.setQueueCapacity(30);               // 有界队列（避免突发堆积）
        executor.setThreadNamePrefix("MusicOpThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 队列满时让调用线程自己执行
        executor.initialize();
        return executor;
    }
}