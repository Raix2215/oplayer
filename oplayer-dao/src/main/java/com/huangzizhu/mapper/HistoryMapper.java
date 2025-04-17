package com.huangzizhu.mapper;

import com.huangzizhu.pojo.history.HistoryQueryParam;
import com.huangzizhu.pojo.history.PlayHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {
    Integer getHistoryCount(HistoryQueryParam param);

    List<PlayHistory> getHistory(HistoryQueryParam param);

    void addHistory(PlayHistory param);
}
