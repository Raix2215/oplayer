package com.huangzizhu.service.impl;

import com.huangzizhu.annotion.UserCheck;
import com.huangzizhu.exception.OperateMusicToLIstFailException;
import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.exception.PlayListException;
import com.huangzizhu.mapper.CollectionMapper;
import com.huangzizhu.mapper.SongMapper;
import com.huangzizhu.pojo.OperateMusicToListParam;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.collection.Collection;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.collection.CollectionQueryForm;
import com.huangzizhu.pojo.collection.UpdateCollectionParam;
import com.huangzizhu.service.CollectionService;
import com.huangzizhu.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@Component
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private SongMapper songMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMusic(OperateMusicToListParam param) {
        Collection collection = checkCollection(param.getUserId());
        Song song = checkSong(param.getSongId());
        try {
            //添加到关系表
            collectionMapper.addMusic(collection.getId(),song.getId());
            //更新collection表的信息
            collection.setTotal(collection.getTotal()+1);
            collection.setDuration(collection.getDuration()+song.getDuration());
            collectionMapper.updateCollectionInfo(collection);
        }catch (DuplicateKeyException e){
            throw new OperateMusicToLIstFailException("歌曲已存在于收藏中",e);
        }
        catch (Exception e){
            throw new OperateMusicToLIstFailException("添加歌曲至收藏失败",e);
        }
    }

    @Override
    public Collection getCollection(Integer userId) {
        return checkCollection(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMusic(OperateMusicToListParam param) {
        Collection collection = checkCollection(param.getUserId());
        Song song = checkSong(param.getSongId());
        try {
            //从关系表删除
            Integer affectRows = collectionMapper.deleteMusic(collection.getId(), song.getId());
            if(affectRows == 0){
                throw new OperateMusicToLIstFailException("歌曲不在收藏列表中");
            }   
            //更新collection表的信息
            collection.setTotal(CommonUtils.max(collection.getTotal()-1,0));
            collection.setDuration(CommonUtils.max(collection.getDuration()-song.getDuration(),0));
            collectionMapper.updateCollectionInfo(collection);
        }catch (OperateMusicToLIstFailException e){
            throw e;
        } catch (Exception e){
            throw new OperateMusicToLIstFailException("从收藏删除歌曲失败",e);
        }
    }

    @Override
    public void updateCollection(UpdateCollectionParam param) {
        Collection collection = checkCollection(param.getUserId());
        //检查参数
        if(CommonUtils.isBlank(param.getDescription())){collection.setDescription("");}
        else collection.setDescription(param.getDescription());
        if(CommonUtils.isBlank(param.getCoverUrl())) {param.setCoverUrl("");}
        else collection.setCoverUrl(param.getCoverUrl());
        collection.setUpdateTime(LocalDateTime.now());
        try {
            collectionMapper.updateCollection(collection);
        } catch (Exception e) {
            throw new PlayListException("更新收藏列表失败",e);
        }
    }

    @Override
    public QueryResult<Song> getSongs(CollectionQueryForm param) {
        Collection collection = checkCollection(param.getUserId());
        Integer total = collection.getTotal();
        param.setStart(param.getPageSize()*(param.getPage()-1));
        List<Song> list = collectionMapper.getSongs(collection.getId(),param);
        return new QueryResult<>(total,list);
    }

    @Override
    public QueryResult<Integer> getMusicIdInCollection(Integer uid) {
        Collection collection = checkCollection(uid);
        List<Integer> list = collectionMapper.getMusicIdInCollection(collection.getId());
        return new QueryResult<>(list.size(),list);
    }

    private Collection checkCollection(Integer id) {
        //根据用户id获取collection
        Collection collection = collectionMapper.getCollection(id);
        if (collection == null) {
            throw new ParamInvalidException("用户id无效,可能传入的用户id有误");
        }
        return collection;
    }
    private Song checkSong(Integer id) {
        //根据歌曲id获取歌曲
        Song song = songMapper.getMusicById(id);
        if (song == null){
            throw new ParamInvalidException("传入的歌曲id有误");
        }
        return song;
    }
}
