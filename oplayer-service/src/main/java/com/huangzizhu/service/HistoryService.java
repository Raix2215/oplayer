package com.huangzizhu.service;

import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.history.HistoryQueryParam;
import com.huangzizhu.pojo.history.PlayHistory;

public interface HistoryService {
    QueryResult<PlayHistory> getHistoryByUserId(HistoryQueryParam param);

    void addHistory(PlayHistory param);
}
