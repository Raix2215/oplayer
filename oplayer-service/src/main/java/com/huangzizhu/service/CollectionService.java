package com.huangzizhu.service;

import com.huangzizhu.pojo.OperateMusicToListParam;
import com.huangzizhu.pojo.collection.Collection;
import com.huangzizhu.pojo.collection.UpdateCollectionParam;

public interface CollectionService {
    void addMusic(OperateMusicToListParam param);

    Collection getCollection(Integer userId);

    void deleteMusic(OperateMusicToListParam param);

    void updateCollection(UpdateCollectionParam param);
}
