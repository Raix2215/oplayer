package com.huangzizhu.mapper;

import com.huangzizhu.pojo.Album;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface AlbumMapper {
    Integer getAlbumId(String albumName, String artist);

    @Insert("insert into album (album_name, artist) values (#{albumName}, #{artist})")
    @Options(useGeneratedKeys = true, keyProperty = "albumId")
    void addAlbum(Album album);
}
