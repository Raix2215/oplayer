package com.huangzizhu.mapper;


import com.huangzizhu.pojo.collection.Collection;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.collection.UpdateCollectionParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface CollectionMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into collection" +
            "        (user_id, description, cover_url, update_time,duration,total)" +
            "values (#{userId}, #{description}, #{coverUrl}, #{updateTime},#{duration},#{total})")
    void addCollection(Collection collection);

    void addMusic(Integer collectionId, Integer songId);

    Collection getCollection(Integer userId);

    List<Song> getSongs(Integer collectionId);

    Integer deleteMusic(Integer collectionId, Integer songId);

    void updateCollection(Collection collection);

    void updateCollectionInfo(Collection collection);
}
