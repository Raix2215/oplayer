package com.huangzizhu.utils;

import com.huangzizhu.exception.MusicDeleteException;
import com.huangzizhu.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class InvalidMusicMarker {
    @Autowired
    @Qualifier("musicDeleteQueue")
    private LinkedBlockingQueue<String> queue;
    @Autowired
    private SongService songService;
    private final int BATCH_SIZE = 10;
    private final int WATERMARK = 10;
    private List<String> list = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int count = 0;

    public void submitMD5(String MD5) {
        try {
            queue.put(MD5);
            lock.lock();
            try {
                count++;
                if (count >= WATERMARK) {
                    condition.signal(); // 达到水位线，唤醒消费者线程
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            throw new MusicDeleteException("删除音乐失败，MD5："+MD5, e);
        }
    }

    @Async("MusicOperationExecutor")
    public void addToDB() {
        log.info("音乐删除线程启动{}", Thread.currentThread().getName());
        while (true) {
            lock.lock();
            try {
                while (count < WATERMARK) {
                    condition.await(); // 等待队列中的元素数量达到水位线
                }
                list.clear();
                int count = queue.drainTo(list, BATCH_SIZE);
                this.count -= count; // 更新计数器
                log.info("新歌曲进入，一次性添加{}", count);
                songService.deleteSongs(list);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 处理中断
            } finally {
                lock.unlock();
            }
        }
    }

    @Scheduled(fixedRate = 1000) // 每1秒检查一次
    public void checkQueue() {
        lock.lock();
        try {
            if (count >= WATERMARK) {
                condition.signal(); // 如果队列中有足够数据，唤醒消费者线程
            }
        } finally {
            lock.unlock();
        }
    }
}