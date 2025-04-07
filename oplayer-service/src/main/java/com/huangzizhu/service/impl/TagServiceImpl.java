package com.huangzizhu.service.impl;

import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.exception.TagException;
import com.huangzizhu.mapper.SongMapper;
import com.huangzizhu.mapper.TagMapper;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Song;
import com.huangzizhu.pojo.tag.Tag;
import com.huangzizhu.pojo.tag.TagCategory;
import com.huangzizhu.pojo.tag.TagForSongParam;
import com.huangzizhu.service.SongService;
import com.huangzizhu.service.TagService;
import com.huangzizhu.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private SongMapper songMapper;

    @Override
    public QueryResult<TagCategory> getTagCategory() {
        List<TagCategory> list = null;
        try {
            list = tagMapper.getTagCategory();
        } catch (Exception e) {
            throw new TagException("获取标签分类失败",e);
        }
        Integer total = list.size();
        return new QueryResult<>(total,list);
    }

    @Override
    public QueryResult<Tag> getTags() {
        List<Tag> list = null;
        try {
            list = tagMapper.getTags();
        } catch (Exception e) {
            throw new TagException("获取标签失败",e);
        }
        Integer total = list.size();
        return new QueryResult<>(total,list);
    }

    @Override
    public QueryResult<Tag> getTags(Integer categoryId) {
        checkCategory(categoryId);
        List<Tag> list = null;
        try {
            list = tagMapper.getTagsByCateId(categoryId);
        } catch (Exception e) {
            throw new TagException("获取标签失败",e);
        }
        Integer total = list.size();
        return new QueryResult<>(total,list);
    }

    @Override
    public Tag getTag(Integer tagId) {
        return checkTag(tagId);
    }

    @Override
    public void addTag(Tag param) {
        checkCategory(param.getCategoryId());
        checkTagIsExist(param);
        checkInfo(param);
        param.setCreateTime(LocalDateTime.now());
        param.setUpdateTime(LocalDateTime.now());
        param.setStatus(1);
        try {
            tagMapper.addTag(param);
        } catch (Exception e) {
            throw new TagException("添加标签失败",e);
        }
    }



    @Override
    public void deleteTag(Integer tagId) {
        checkTag(tagId);
        try {
            tagMapper.deleteTag(tagId);
        } catch (Exception e) {
            throw new TagException("删除标签失败",e);
        }
    }

    @Override
    public void updateTag(Tag param) {
        checkCategory(param.getCategoryId());
        checkTag(param.getId());
        checkTagIsExist(param);
        checkInfo(param);
        param.setUpdateTime(LocalDateTime.now());
        param.setStatus(1);
        try {
            tagMapper.updateTag(param);
        } catch (Exception e) {
            throw new TagException("更新标签失败",e);
        }
    }

    @Override
    public void addTagForMusic(TagForSongParam param) {
        checkSong(param.getSongId());
        checkTag(param.getTagId());
        try {
            tagMapper.addTagForSong(param);
        }catch (DuplicateKeyException e){
          throw new TagException("该歌曲已存在该标签",e);
        } catch (Exception e) {
            throw new TagException("为歌曲添加标签失败",e);
        }
    }

    @Override
    public void deleteTagForMusic(TagForSongParam param) {
        checkTag(param.getTagId());
        checkSong(param.getSongId());
        Integer affectRows = null;
        try {
            affectRows = tagMapper.deleteTagForMusic(param);
        } catch (Exception e) {
            throw new TagException("为歌曲删除标签失败",e);
        }
        if (affectRows == 0) throw new TagException("该歌曲没有这个标签");
    }

    @Override
    public QueryResult<Tag> getTagsBySongId(Integer songId) {
        checkSong(songId);
        List<Tag> list = null;
        try {
            list = tagMapper.getTagsBySongId(songId);
        } catch (Exception e) {
            throw new TagException("获取歌曲标签失败",e);
        }
        Integer total = list.size();
        return new QueryResult<>(total,list);
    }

    private Tag checkTag(Integer tagId) {
        Tag tag = tagMapper.getTag(tagId);
        if (tag == null) {
            throw new ParamInvalidException("标签不存在");
        }
        return tag;
    }
    private void checkTagIsExist(Tag tag) {
        Tag res = tagMapper.getTagByTag(tag);
        if (res != null) {
            throw new ParamInvalidException("标签已存在");
        }
    }

    private Song checkSong(Integer id) {
        Song song = songMapper.getMusicById(id);
        if (song == null) {
            throw new ParamInvalidException("歌曲不存在");
        }
        return song;
    }

    private TagCategory checkCategory(Integer categoryId) {
        TagCategory tagCategory = tagMapper.getTagCategoryById(categoryId);
        if (tagCategory == null) {
            throw new ParamInvalidException("分类不存在");
        }
        return tagCategory;
    }

    private void checkInfo(Tag param) {
        if(CommonUtils.isBlank(param.getName())) throw new ParamInvalidException("标签名称不能为空");
        if(CommonUtils.isBlank(param.getDescription())) throw new ParamInvalidException("标签描述不能为空");
    }
}
