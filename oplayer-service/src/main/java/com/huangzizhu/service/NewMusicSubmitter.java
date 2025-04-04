package com.huangzizhu.utils;

import com.huangzizhu.exception.MusicParseException;
import com.huangzizhu.exception.UnsupportedMusicFormat;
import com.huangzizhu.pojo.MusicFileFeature;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
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
    private final int BATCH_SIZE = 10;
    private final int WATERMARK = 10;
    private List<Song> list = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int count = 0;

    public void submitMusic(MusicFileFeature music) {
        File musicFile = new File(music.getPath());
        Song song = getSong(musicFile);
        File newFile = new File(musicFile.getParent() + File.separator + music.getMd5() + "." + song.getFormat());
        if (!musicFile.renameTo(newFile)) throw new MusicParseException("重命名文件失败", musicFile);
        song.setPath(newFile.getAbsolutePath());
        song.setMd5(music.getMd5());
        try {
            queue.put(song);
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
            throw new MusicParseException("提交新音乐失败", newFile, e);
        }
    }

    private static Song getSong(File musicFile) {
        if(musicFile.getName().endsWith(".mp3")){
            return MusicFileParser.mp3Parse(musicFile);
        }
        if(musicFile.getName().endsWith(".wav")){
            return MusicFileParser.wavParse(musicFile);
        }
        if(musicFile.getName().endsWith(".flac")){
            return MusicFileParser.flacParse(musicFile);
        }
        throw new UnsupportedMusicFormat("不支持的音乐格式");
    }

    @Async("MusicOperationExecutor")
    public void addToDB() {
        log.info("音乐提交线程启动{}", Thread.currentThread().getName());
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
                songService.addSongs(list);
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