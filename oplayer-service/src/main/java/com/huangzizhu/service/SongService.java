package com.huangzizhu.service;

import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.music.MusicQueryForm;
import com.huangzizhu.pojo.music.SimpleMusicInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public interface SongService {
    HashSet<String> getAllMD5();

    void addSongs(List<Song> songsToAdd);


    void updateSongs(List<Song> songs);

    void markInvalid(List<String> list);

    Resource getMusicResource(String md5);

    ResponseEntity<StreamingResponseBody> handlePartialContent(Resource resource, String rangeHeader) throws IOException;

    ResponseEntity<Resource> downloadMusic(String md5) throws IOException;

    QueryResult<Song> getAllMusic(MusicQueryForm param);

    Song getMusicById(Integer id);

    List<SimpleMusicInfo> fuzzySearch(String name);

    QueryResult<Song> getMusicByTagId(MusicQueryForm param);

    QueryResult<Song> getMusic(MusicQueryForm param);

    void uploadMusic(MultipartFile file);

    QueryResult<Song> getRecommendMusic(Integer count);

    QueryResult<Song> getDailyMusic(Integer userId);
}
