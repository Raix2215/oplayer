package com.huangzizhu.service;

import com.huangzizhu.exception.MusicDeleteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
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
    private final long FORCE_SUBMIT_INTERVAL = 3000; // 3 seconds
    private List<String> list = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int count = 0;
    private long lastDeleteTime = System.currentTimeMillis();

    private volatile boolean running = true;

    @Async("MusicOperationExecutor")
    public void start() {
        log.info("音乐删除线程启动{}", Thread.currentThread().getName());
        while (running) {
            lock.lock();
            try {
                // 检查是否达到水位线或超过强制删除间隔
                boolean shouldDelete = count >= WATERMARK ||
                        (System.currentTimeMillis() - lastDeleteTime) >= FORCE_SUBMIT_INTERVAL;

                if (!shouldDelete) {
                    // 等待直到达到条件或超时
                    long remainingWait = FORCE_SUBMIT_INTERVAL - (System.currentTimeMillis() - lastDeleteTime);
                    if (remainingWait > 0) {
                        condition.await(remainingWait, TimeUnit.MILLISECONDS);
                    }
                    continue;
                }

                // 执行删除操作
                list.clear();
                int drainedCount = queue.drainTo(list, BATCH_SIZE);
                if (drainedCount > 0) {
                    this.count -= drainedCount;
                    lastDeleteTime = System.currentTimeMillis();
                    log.info("无效音乐删除，一次性删除{}条", drainedCount);
                    songService.markInvalid(list);
                } else if ((System.currentTimeMillis() - lastDeleteTime) >= FORCE_SUBMIT_INTERVAL) {
                    // 即使队列为空，也更新最后删除时间以保持节奏
                    lastDeleteTime = System.currentTimeMillis();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("线程被中断", e);
                running = false;
            } finally {
                lock.unlock();
            }
        }
        log.info("音乐删除线程退出{}", Thread.currentThread().getName());
    }

    public void submitMD5(String MD5) {
        try {
            queue.put(MD5);
            lock.lock();
            try {
                count++;
                if (count >= WATERMARK) {
                    condition.signal();
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            throw new MusicDeleteException("删除音乐失败，MD5：" + MD5, e);
        }
    }

    public void stop() {
        running = false;
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }


}