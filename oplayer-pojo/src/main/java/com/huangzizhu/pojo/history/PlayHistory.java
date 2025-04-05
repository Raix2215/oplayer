package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 播放历史
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayHistory {
    private Long id;
    private Long userId;
    private Long songId;
    private Integer progress;//%
    private String ip;
    private Integer playMode;
    private Integer volume;
    private Integer isPaused;
    private LocalDateTime playTime;
    private Integer listId;
    private Integer listType;
}
