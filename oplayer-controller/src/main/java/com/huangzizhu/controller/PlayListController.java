package com.huangzizhu.controller;

import com.huangzizhu.annotion.UserCheck;
import com.huangzizhu.pojo.OperateMusicToListParam;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Result;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.playList.PlayListQueryForm;
import com.huangzizhu.pojo.playList.Playlist;
import com.huangzizhu.service.PlayListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/list")
public class PlayListController {

    @Autowired
    private PlayListService playListService;

    @UserCheck(field = "creatorId")
    @PostMapping()
    public Result createPlayList(@RequestBody Playlist param){
        log.info("创建歌单请求: {}", param);
        playListService.createPlayList(param);
        return Result.success();
    }
    @PostMapping("/all")
    public Result getAllPlayList(@RequestBody PlayListQueryForm param){
        log.info("获取所有歌单请求: {}", param);
        QueryResult<Playlist> data = playListService.getAllPlayList(param);
        return Result.success(data);
    }
    @UserCheck
    @PostMapping("/music")
    public Result addMusicToPlayList(@RequestBody OperateMusicToListParam param){
        log.info("添加歌曲到歌单请求: {}", param);
        playListService.addMusicToPlayList(param);
        return Result.success();
    }
    @PostMapping("/music/all")
    public Result getAllMusic(@RequestBody PlayListQueryForm param){
        log.info("获取歌单内所有歌曲请求: {}", param);
        QueryResult<Song> data = playListService.getAllMusic(param);
        return Result.success(data);
    }
    @GetMapping("/{id}")
    public Result getPlayList(@PathVariable Integer id){
        log.info("获取歌单详情请求: {}", id);
        Playlist data = playListService.getPlayList(id);
        return Result.success(data);
    }
    @UserCheck
    @DeleteMapping("/music")
    public Result deleteMusicFromPlayList(@RequestBody OperateMusicToListParam param){
        log.info("删除歌单内歌曲请求: {}", param);
        playListService.deleteMusicFromPlayList(param);
        return Result.success();
    }
    @UserCheck(field = "creatorId")
    @PutMapping()
    public Result updatePlayList(@RequestBody Playlist param){
        log.info("更新歌单请求: {}", param);
        playListService.updatePlayList(param);
        return Result.success();
    }
    @UserCheck(field = "creatorId")
    @DeleteMapping()
    public Result deletePlayList(@RequestBody Playlist param){
        log.info("删除歌单请求: {}", param);
        playListService.deletePlayList(param);
        return Result.success();
    }

}
