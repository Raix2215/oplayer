package com.huangzizhu.mapper;

import com.huangzizhu.pojo.Says;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SentenceMapper {

    Integer getCount();

    Says getSays(int randId);
}
