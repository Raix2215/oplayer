package com.huangzizhu.controller;

import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Result;
import com.huangzizhu.pojo.tag.TagForSongParam;
import com.huangzizhu.pojo.tag.Tag;
import com.huangzizhu.pojo.tag.TagCategory;
import com.huangzizhu.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/category")
    public Result getTagCategory() {
        log.info("获取标签分类");
        QueryResult<TagCategory> data = tagService.getTagCategory();
        return Result.success(data);
    }
    @GetMapping("/list")
    public Result getTagList() {
        log.info("获取标签列表");
        QueryResult<Tag> data = tagService.getTags();
        return Result.success(data);
    }
    @GetMapping("/list/{categoryId}")
    public Result getTagList(@PathVariable Integer categoryId) {
        log.info("根据分类id获取标签列表{}", categoryId);
        QueryResult<Tag> data = tagService.getTags(categoryId);
        return Result.success(data);
    }
    @GetMapping("/{tagId}")
    public Result getTag(@PathVariable Integer tagId) {
        log.info("根据id获取标签{}", tagId);
        Tag data = tagService.getTag(tagId);
        return Result.success(data);
    }
    @PostMapping()
    public Result addTag(@RequestBody Tag param){
        log.info("添加标签{}", param);
        tagService.addTag(param);
        return Result.success();
    }
    @DeleteMapping("/{tagId}")
    public Result deleteTag(@PathVariable Integer tagId){
        log.info("删除标签{}", tagId);
        tagService.deleteTag(tagId);
        return Result.success();
    }
    @PutMapping()
    public Result updateTag(@RequestBody Tag param){
        log.info("更新标签{}", param);
        tagService.updateTag(param);
        return Result.success();
    }
    @PostMapping("/music")
    public Result addTagForMusic(@RequestBody TagForSongParam param){
        log.info("为歌曲添加标签{}", param);
        tagService.addTagForMusic(param);
        return Result.success();
    }
    @DeleteMapping("/music")
    public Result deleteTagForMusic(@RequestBody TagForSongParam param){
        log.info("为歌曲删除标签{}", param);
        tagService.deleteTagForMusic(param);
        return Result.success();
    }
    @GetMapping("/music/{songId}")
    public Result getTagForMusic(@PathVariable Integer songId){
        log.info("获取歌曲的标签{}", songId);
        QueryResult<Tag> data = tagService.getTagsBySongId(songId);
        return Result.success(data);
    }

}
