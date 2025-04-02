package com.huangzizhu.app.init;

import com.huangzizhu.pojo.Song;
import com.huangzizhu.service.SongService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ArrayBlockingQueue;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class NewMusicAdder implements Runnable {

    @Autowired
    private SongService songService;
    private ArrayBlockingQueue<Song> queue;

    @Override
    public void run() {
        while (true) {
            try {
                Song songToAdd = queue.take();
                songService.addSong(songToAdd);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
