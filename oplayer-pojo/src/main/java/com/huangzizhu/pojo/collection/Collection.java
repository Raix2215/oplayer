package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 收藏表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private Integer id; // 收藏ID（唯一标识）
    private Integer userId; // 用户ID（逻辑外键，关联到用户表）
    private LocalDateTime updateTime; // 更新时间
    private String description; // 描述
    private String coverUrl; // 封面图片URL
    private Integer duration; // 时长（总时长，秒）
    private List<Song> list; // 收藏的歌曲列表
}