package com.huangzizhu.pojo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id; // 评论ID（唯一标识）
    private Integer songId; // 歌曲ID（逻辑外键，关联到歌曲表）
    private Integer userId; // 用户ID（逻辑外键，关联到用户表）
    private String txt; // 评论内容
    private LocalDateTime time; // 评论时间
    private Integer count; // 点赞数
    private String ip; // 评论IP
}