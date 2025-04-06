package com.huangzizhu.app.config;

import com.huangzizhu.pojo.Song;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class MusicOperateQueueConfig {
    @Bean
    public LinkedBlockingQueue<Song> musicAddQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Bean
    public LinkedBlockingQueue<String> musicDeleteQueue() {return new LinkedBlockingQueue<>();}


}
