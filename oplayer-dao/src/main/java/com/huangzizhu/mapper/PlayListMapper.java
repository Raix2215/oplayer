package com.huangzizhu.mapper;

import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.playList.PlayListQueryForm;
import com.huangzizhu.pojo.playList.Playlist;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PlayListMapper {
    void createPlayList(Playlist param);

    Integer getAllPlayListCount(Integer userId);

    List<Playlist> getAllPlayList(PlayListQueryForm param);

    Playlist getPlayList(Integer id);

    void addMusic(Integer listId, Integer songId, LocalDateTime addTime);

    void updatePlayListCount(Playlist playlist);

    Integer getAllMusicCount(Integer listId);

    List<Song> getAllMusic(PlayListQueryForm param);

    Integer deleteMusic(Integer listId, Integer songId);

    void updatePlayList(Playlist param);

    void deleteAllMusic(Integer id);

    void deletePlayList(Integer id);
}
