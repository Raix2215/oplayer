package com.huangzizhu.mapper;

import com.huangzizhu.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    void insert(OperationLog operateLog);
}
