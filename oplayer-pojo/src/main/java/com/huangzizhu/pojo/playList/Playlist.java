package com.huangzizhu.pojo.playList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 歌单表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    private Integer id; // 歌单ID（唯一标识）
    private String name; // 歌单名称
    private Integer creatorId; // 创建者ID（逻辑外键，关联到用户表）
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String description; // 歌单描述`
    private String coverUrl; // 歌单封面图片URL
    private Integer totalDuration; // 歌单时长（总时长，秒）
    private Integer total; // 歌单歌曲数量
}