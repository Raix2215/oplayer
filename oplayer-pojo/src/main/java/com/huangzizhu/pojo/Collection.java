package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 收藏表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private Long id; // 收藏ID（唯一标识）
    private Long userId; // 用户ID（逻辑外键，关联到用户表）
    private Long songId; // 歌曲ID（逻辑外键，关联到歌曲表）
    private LocalDateTime updateTime; // 更新时间
    private String description; // 描述
    private String coverUrl; // 封面图片URL
    private Integer duration; // 时长（总时长，秒）
}