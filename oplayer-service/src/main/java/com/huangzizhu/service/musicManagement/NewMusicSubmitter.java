package com.huangzizhu.service;

import com.huangzizhu.exception.MusicParseException;
import com.huangzizhu.pojo.MusicFileFeature;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.utils.MusicFileParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class NewMusicSubmitter {
    @Autowired
    @Qualifier("musicAddQueue")
    private LinkedBlockingQueue<Song> queue;
    @Autowired
    private SongService songService;
    @Autowired
    private MusicFileParser musicFileParser;

    private final int BATCH_SIZE = 10;
    private final int WATERMARK = 10;
    private final long FORCE_SUBMIT_INTERVAL = 3000; // 3 seconds
    private List<Song> list = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int count = 0;
    private long lastSubmitTime = System.currentTimeMillis();

    private volatile boolean running = true;


    @Async("MusicOperationExecutor")
    public void start() {
        log.info("音乐提交线程启动{}", Thread.currentThread().getName());
        while (running) {
            lock.lock();
            try {
                // 检查是否达到水位线或超过强制提交间隔
                boolean shouldSubmit = count >= WATERMARK ||
                        (System.currentTimeMillis() - lastSubmitTime) >= FORCE_SUBMIT_INTERVAL;

                if (!shouldSubmit) {
                    // 等待直到达到条件或超时
                    long remainingWait = FORCE_SUBMIT_INTERVAL - (System.currentTimeMillis() - lastSubmitTime);
                    if (remainingWait > 0) {
                        condition.await(remainingWait, TimeUnit.MILLISECONDS);
                    }
                    continue;
                }

                // 执行提交
                list.clear();
                int drainedCount = queue.drainTo(list, BATCH_SIZE);
                if (drainedCount > 0) {
                    this.count -= drainedCount;
                    lastSubmitTime = System.currentTimeMillis();
                    log.info("新歌曲进入，一次性添加{}", drainedCount);
                    songService.addSongs(list);
                } else if ((System.currentTimeMillis() - lastSubmitTime) >= FORCE_SUBMIT_INTERVAL) {
                    // 即使队列为空，也更新最后提交时间以保持节奏
                    lastSubmitTime = System.currentTimeMillis();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("线程被中断", e);
                running = false;
            } finally {
                lock.unlock();
            }
        }
        log.info("音乐提交线程退出{}", Thread.currentThread().getName());
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
    /**
     * 完全异步的音乐提交方法
     * @param music 音乐文件特征
     * @return CompletableFuture 异步结果
     */
    public CompletableFuture<Void> submitMusic(MusicFileFeature music) {
        File musicFile = new File(music.getPath());

        return musicFileParser.parseAsync(musicFile)
                .thenApply(song -> renameAndPrepareSong(musicFile, music, song))
                .thenCompose(this::enqueueSongAsync)
                .exceptionally(e -> handleSubmissionException(e, musicFile));
    }

    /**
     * 重命名文件并准备Song对象
     */
    private Song renameAndPrepareSong(File originalFile, MusicFileFeature music, Song song) {
        File newFile = createRenamedFile(originalFile, music.getMd5(), song.getFormat());
        performFileRename(originalFile, newFile);
        updateSongPathAndMd5(song, newFile, music.getMd5());
        return song;
    }

    /**
     * 创建重命名后的文件对象
     */
    private File createRenamedFile(File originalFile, String md5, String format) {
        return new File(
                originalFile.getParent() + File.separator + md5 + "." + format
        );
    }

    /**
     * 执行文件重命名操作
     */
    private void performFileRename(File source, File target) {
        if (!source.renameTo(target)) {
            throw new MusicParseException("重命名文件失败", source);
        }
    }

    /**
     * 更新Song对象的路径和MD5
     */
    private void updateSongPathAndMd5(Song song, File newFile, String md5) {
        song.setPath(newFile.getAbsolutePath());
        song.setMd5(md5);
    }

    /**
     * 异步将歌曲加入队列
     */
    private CompletableFuture<Void> enqueueSongAsync(Song song) {
        return CompletableFuture.runAsync(() -> {
            try {
                queue.put(song);
                signalWatermark();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new MusicParseException("提交队列中断", new File(song.getPath()), e);
            }
        });
    }

    /**
     * 统一处理异常
     */
    private Void handleSubmissionException(Throwable e, File musicFile) {
        Throwable cause = e instanceof CompletionException ? e.getCause() : e;
        if (cause instanceof MusicParseException) {
            throw (MusicParseException) cause;
        }
        throw new MusicParseException("提交音乐失败", musicFile, cause);
    }

    /**
     * 检查并触发水位线信号
     */
    private void signalWatermark() {
        lock.lock();
        try {
            count++;
            if (count >= WATERMARK) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

}