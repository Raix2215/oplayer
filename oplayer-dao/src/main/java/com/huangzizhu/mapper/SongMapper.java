package com.huangzizhu.mapper;

import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.music.MusicQueryForm;
import com.huangzizhu.pojo.music.SimpleMusicInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashSet;
import java.util.List;

@Mapper
public interface SongMapper {
    HashSet<String> getAllMD5();

    void addSongs(List<Song> songsToAdd);

    void markInvalid(List<String> md5);

    void deleteSongs(List<String> list);

    List<String> getMD5sNotExist(List<String> list);

    void updateSong(Song song);

    String getSongByMD5(String md5);

    Integer getTotal(MusicQueryForm param);

    List<Song> getAllMusic(MusicQueryForm param);

    Song getMusicById(Integer id);

    List<SimpleMusicInfo> fuzzySearch(String name);

    Integer getMusicDuration(Integer songId);

    Integer getMusicCountByTagId(Integer tagId);

    List<Song> getMusicByTagId(MusicQueryForm param);

    Integer getMusicCount(MusicQueryForm param);

    List<Song> getMusic(MusicQueryForm param);
}
