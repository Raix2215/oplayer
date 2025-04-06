package com.huangzizhu.service.impl;

import com.huangzizhu.exception.OperateMusicToLIstFailException;
import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.exception.UserNotFoundException;
import com.huangzizhu.mapper.CollectionMapper;
import com.huangzizhu.mapper.SongMapper;
import com.huangzizhu.pojo.OperateMusicToListParam;
import com.huangzizhu.pojo.collection.Collection;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.collection.UpdateCollectionParam;
import com.huangzizhu.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //根据用户id获取collection表的id
        Integer collectionId = collectionMapper.getId(param.getUserId());
        if (collectionId == null) {
            throw new OperateMusicToLIstFailException("获取collection表的id失败,可能传入的用户id有误");
        }
        //根据歌曲id获取歌曲的时长
        Integer duration = songMapper.getMusicDuration(param.getSongId());
        if (duration == null){
            throw new OperateMusicToLIstFailException("传入的歌曲id有误");
        }
        try {
            //添加到关系表
            collectionMapper.addMusic(collectionId, param.getSongId());
            //更新collection表的信息
            collectionMapper.updateCollectionDuration(collectionId, duration, 1);
        }catch (DuplicateKeyException e){
            throw new OperateMusicToLIstFailException("歌曲已存在于收藏中",e);
        }
        catch (OperateMusicToLIstFailException e){
            throw e;
        }
        catch (Exception e){
            throw new OperateMusicToLIstFailException("添加歌曲至收藏失败",e);
        }
    }

    @Override
    public Collection getCollection(Integer userId) {
        //根据collectionId获取收藏列表
        Collection collection = collectionMapper.getCollection(userId);
        if (collection == null){
            throw new UserNotFoundException("获取用户收藏列表失败,可能传入的用户id有误");
        }
        List<Song> list = collectionMapper.getSongs(collection.getId());
        collection.setList(list);
        collection.setId(null);
        collection.setTotal(list.size());
        return collection;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMusic(OperateMusicToListParam param) {
        //根据用户id获取collection表的id
        Integer collectionId = collectionMapper.getId(param.getUserId());
        if (collectionId == null) {
            throw new OperateMusicToLIstFailException("获取collection表的id失败,可能传入的用户id有误");
        }
        //根据歌曲id获取歌曲的时长
        Integer duration = songMapper.getMusicDuration(param.getSongId());
        if (duration == null){
            throw new OperateMusicToLIstFailException("传入的歌曲id有误");
        }
        try {
            //从关系表删除
            Integer affectedRows = collectionMapper.deleteMusic(collectionId, param.getSongId());
            if(affectedRows == 0) throw new OperateMusicToLIstFailException("收藏中不存在该歌曲");
            //更新collection表的信息
            collectionMapper.updateCollectionDuration(collectionId, duration, -1);
        } catch (OperateMusicToLIstFailException e){
            throw e;
        }catch (Exception e){
            throw new OperateMusicToLIstFailException("从收藏删除歌曲失败",e);
        }
    }

    @Override
    public void updateCollection(UpdateCollectionParam param) {
        //检查参数
        if(param.getDescription() == null && param.getCoverUrl() == null){
            throw new ParamInvalidException("description和coverUrl不能同时为空");
        }
        //根据用户id获取collection表的id
        Integer collectionId = collectionMapper.getId(param.getUserId());
        if (collectionId == null) {
            throw new ParamInvalidException("获取collection表的id失败,可能传入的用户id有误");
        }
        param.setId(collectionId);
        collectionMapper.updateCollection(param);
    }
}
