package com.huangzizhu.app;

import ch.qos.logback.classic.Level;
import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.huangzizhu","com.huangzizhu.pojo","com.huangzizhu.utils"})
@SpringBootApplication
@MapperScan("com.huangzizhu.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
    @PostConstruct
    public void disableJAudioTaggerLogging() {
        // 处理 java.util.logging
        java.util.logging.Logger.getLogger("org.jaudiotagger").setLevel(java.util.logging.Level.OFF);

        // 处理 SLF4J
        if(LoggerFactory.getLogger("org.jaudiotagger") instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger)LoggerFactory.getLogger("org.jaudiotagger")).setLevel(Level.OFF);
        }
    }
}
