package com.huangzizhu.service.impl;

import com.huangzizhu.mapper.HistoryMapper;
import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.history.HistoryQueryParam;
import com.huangzizhu.pojo.history.PlayHistory;
import com.huangzizhu.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public QueryResult<PlayHistory> getHistoryByUserId(HistoryQueryParam param) {
        //设置分页参数
        param.setStart((param.getPage() - 1) * param.getPageSize());
        //获取总数
        Integer total = historyMapper.getHistoryCount(param);
        //获取数据
        List<PlayHistory> list = historyMapper.getHistory(param);
        //封装数据
        QueryResult<PlayHistory> data = new QueryResult<>();
        data.setTotal(total);
        data.setList(list);
        return data;
    }

    @Override
    public void addHistory(PlayHistory param) {
        param.setPlayTime(LocalDateTime.now());
        historyMapper.addHistory(param);
    }
}
