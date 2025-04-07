package com.huangzizhu.service;

import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.tag.Tag;
import com.huangzizhu.pojo.tag.TagCategory;

public interface TagService {
    QueryResult<TagCategory> getTagCategory();

    QueryResult<Tag> getTags();

    QueryResult<Tag> getTags(Integer categoryId);

    Tag getTag(Integer tagId);

    void addTag(Tag param);

    void deleteTag(Integer tagId);

    void updateTag(Tag param);
}
