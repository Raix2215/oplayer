package com.huangzizhu.service.impl;

import com.huangzizhu.exception.OperateMusicToLIstFailException;
import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.exception.PlayListException;
import com.huangzizhu.exception.UserNotFoundException;
import com.huangzizhu.mapper.PlayListMapper;
import com.huangzizhu.mapper.SongMapper;
import com.huangzizhu.mapper.UserMapper;
import com.huangzizhu.pojo.OperateMusicToListParam;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.playList.PlayListQueryForm;
import com.huangzizhu.pojo.playList.Playlist;
import com.huangzizhu.pojo.user.User;
import com.huangzizhu.service.PlayListService;
import com.huangzizhu.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PlayListServiceImpl implements PlayListService {
    @Autowired
    private PlayListMapper playListMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SongMapper songMapper;

    @Override
    public void createPlayList(Playlist param) {
        checkUser(param.getCreatorId());
        processListInfo(param);
        //设置参数
        param.setCreateTime(LocalDateTime.now());
        param.setTotalDuration(0);
        param.setTotal(0);
        try {
            playListMapper.createPlayList(param);
        }catch (Exception e) {
            throw new PlayListException("创建歌单失败", e);
        }
    }

    @Override
    public QueryResult<Playlist> getAllPlayList(PlayListQueryForm param) {
        checkUser(param.getUserId());
        //计算分页参数
        param.setStart(param.getPageSize()*(param.getPage()-1));
        Integer total = null;
        List<Playlist> list = null;
        try {
            total = playListMapper.getAllPlayListCount(param.getUserId());
            list = playListMapper.getAllPlayList(param);
        } catch (Exception e) {
            throw new PlayListException("获取所有歌单失败", e);
        }
        return new QueryResult<>(total, list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMusicToPlayList(OperateMusicToListParam param) {
        Song song = checkSong(param.getSongId());
        Playlist playlist = checkList(param.getListId());
        try {
            //向关系表中插入数据
            playListMapper.addMusic(param.getListId(), param.getSongId(),LocalDateTime.now());
            //设置更新后的参数
            playlist.setTotal(playlist.getTotal() + 1);
            playlist.setTotalDuration(playlist.getTotalDuration() + song.getDuration());
            //更新歌单的歌曲数量
            playListMapper.updatePlayListCount(playlist);
        }catch (DuplicateKeyException e){
            throw new OperateMusicToLIstFailException("歌单中已存在该歌曲", e);
        } catch (Exception e) {
            throw new OperateMusicToLIstFailException("添加歌曲到歌单失败", e);
        }
    }

    @Override
    public QueryResult<Song> getAllMusic(PlayListQueryForm param) {
        Playlist playlist = checkList(param.getPlayListId());
        //计算分页参数
        param.setStart(param.getPageSize()*(param.getPage()-1));
        Integer total = null;
        List<Song> list = null;
        try {
            total = playListMapper.getAllMusicCount(playlist.getId());
            list = playListMapper.getAllMusic(param);
        } catch (Exception e) {
            throw new PlayListException("获取歌单内所有歌曲失败", e);
        }
        return new QueryResult<>(total, list);
    }

    @Override
    public Playlist getPlayList(Integer id) {
        checkList(id);
        try {
            return playListMapper.getPlayList(id);
        } catch (Exception e) {
            throw new PlayListException("获取歌单详情失败", e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMusicFromPlayList(OperateMusicToListParam param) {
        Playlist playlist = checkList(param.getListId());
        Song song = checkSong(param.getSongId());
        try {
            //从关系表中删除数据
            Integer affectedRows = playListMapper.deleteMusic(param.getListId(), param.getSongId());
            if (affectedRows == 0) throw new OperateMusicToLIstFailException("歌单中不存在该歌曲");
            //设置更新后的参数
            playlist.setTotal(CommonUtils.max(playlist.getTotal() - 1,0));
            playlist.setTotalDuration(CommonUtils.max(playlist.getTotalDuration() - song.getDuration(),0));
            //更新歌单的歌曲数量
            playListMapper.updatePlayListCount(playlist);
        }catch (OperateMusicToLIstFailException e){
            throw e;
        }
        catch (Exception e) {
            throw new OperateMusicToLIstFailException("从歌单中删除歌曲失败", e);
        }
    }

    @Override
    public void updatePlayList(Playlist param) {
        checkList(param.getId());
        processListInfo(param);
        try {
            playListMapper.updatePlayList(param);
        } catch (Exception e) {
            throw new PlayListException("更新歌单失败", e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePlayList(Playlist param) {
        checkList(param.getId());
        try {
            //删除歌单内所有歌曲
            playListMapper.deleteAllMusic(param.getId());
            //删除歌单
            playListMapper.deletePlayList(param.getId());
        } catch (Exception e) {
            throw new PlayListException("删除歌单失败", e);
        }
    }

    private Playlist checkList (Integer id) {
        Playlist playlist = playListMapper.getPlayList(id);
        if(playlist == null){
            throw new ParamInvalidException("歌单不存在");
        }
        return playlist;
    }

    private Song checkSong(Integer id) {
        Song song = songMapper.getMusicById(id);
        if (song == null) {
            throw new ParamInvalidException("歌曲不存在");
        }
        return song;
    }


    private User checkUser(Integer id) {
        //检查用户是否存在
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }
        return user;
    }

    private void processListInfo(Playlist playlist) {
        //检查歌单名称是否为空
        if (CommonUtils.isBlank(playlist.getName())) throw new ParamInvalidException("歌单名称不能为空");
        if (CommonUtils.isBlank(playlist.getDescription())) playlist.setDescription("");
        if (CommonUtils.isBlank(playlist.getCoverUrl())) playlist.setCoverUrl("");
        //设置参数
        playlist.setUpdateTime(LocalDateTime.now());
    }
}
