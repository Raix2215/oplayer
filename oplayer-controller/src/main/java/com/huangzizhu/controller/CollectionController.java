package com.huangzizhu.controller;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.huangzizhu.annotion.UserCheck;
import com.huangzizhu.pojo.OperateMusicToListParam;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.collection.Collection;
import com.huangzizhu.pojo.Result;
import com.huangzizhu.pojo.collection.CollectionQueryForm;
import com.huangzizhu.pojo.collection.UpdateCollectionParam;
import com.huangzizhu.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @UserCheck
    @PostMapping("/music")
    public Result addMusic(@RequestBody OperateMusicToListParam param) {
        log.info("添加歌曲至收藏: {}", param);
        collectionService.addMusic(param);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getCollection(@PathVariable Integer id) {
        log.info("获取用户收藏列表: {}", id);
        Collection data = collectionService.getCollection(id);
        return Result.success(data);
    }
    @UserCheck
    @DeleteMapping("/music")
    public Result deleteMusic(@RequestBody OperateMusicToListParam param) {
        log.info("删除收藏列表中的歌曲: {}", param);
        collectionService.deleteMusic(param);
        return Result.success();
    }
    @UserCheck
    @PutMapping
    public Result updateCollection(@RequestBody UpdateCollectionParam param) {
        log.info("更新收藏列表: {}", param);
        collectionService.updateCollection(param);
        return Result.success();
    }
    @PostMapping("/music/get")
    public Result getSongs(@RequestBody CollectionQueryForm param) {
        log.info("获取收藏列表中的歌曲: {}", param);
        QueryResult<Song> data = collectionService.getSongs(param);
        return Result.success(data);
    }
}
