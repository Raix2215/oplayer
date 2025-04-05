package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户行为数据表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBehavior {
    private Long id; // 行为ID（唯一标识）
    private Long userId; // 用户ID（逻辑外键）
    private Long collectionId; // 收藏列表ID（与收藏列表表关联）
    private Integer totalPlayDuration; // 播放时长统计（秒）
    private Integer totalPlayCount; // 播放数目统计
    private LocalDateTime lastLoginTime; // 最后登录时间
}