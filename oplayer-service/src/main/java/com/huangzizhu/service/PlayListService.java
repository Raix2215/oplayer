package com.huangzizhu.service;

import com.huangzizhu.pojo.OperateMusicToListParam;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.playList.PlayListQueryForm;
import com.huangzizhu.pojo.playList.Playlist;

public interface PlayListService {
    void createPlayList(Playlist param);

    QueryResult<Playlist> getAllPlayList(PlayListQueryForm param);

    void addMusicToPlayList(OperateMusicToListParam param);

    QueryResult<Song> getAllMusic(PlayListQueryForm param);

    Playlist getPlayList(Integer id);

    void deleteMusicFromPlayList(OperateMusicToListParam param);

    void updatePlayList(Playlist param);

    void deletePlayList(Playlist param);
}
