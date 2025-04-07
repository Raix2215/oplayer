package com.huangzizhu.mapper;

import com.huangzizhu.pojo.tag.Tag;
import com.huangzizhu.pojo.tag.TagCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    List<TagCategory> getTagCategory();

    List<Tag> getTags();


    List<Tag> getTagsByCateId(Integer categoryId);

    TagCategory getTagCategoryById(Integer categoryId);

    Tag getTag(Integer tagId);

    Tag getTagByTag(Tag tag);

    void addTag(Tag param);

    void deleteTag(Integer tagId);

    void updateTag(Tag param);
}
