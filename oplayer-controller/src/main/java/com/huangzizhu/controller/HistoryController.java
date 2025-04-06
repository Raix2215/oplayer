package com.huangzizhu.controller;

import com.huangzizhu.pojo.QueryResult;
import com.huangzizhu.pojo.Result;
import com.huangzizhu.pojo.history.HistoryQueryParam;
import com.huangzizhu.pojo.history.PlayHistory;
import com.huangzizhu.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    @PostMapping("/query")
    public Result getHistoryByUserId(@RequestBody HistoryQueryParam param) {
        log.info("获取用户{}的历史记录", param);
        QueryResult<PlayHistory> data = historyService.getHistoryByUserId(param);
        return Result.success(data);
    }
    @PostMapping("/submit")
    public Result addHistory(@RequestBody PlayHistory param){
        log.info("添加用户{}的历史记录", param);
        historyService.addHistory(param);
        return Result.success();
    }


}
