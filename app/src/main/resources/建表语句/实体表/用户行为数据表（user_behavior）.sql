CREATE TABLE `user_behavior` (
                                 `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '行为ID（唯一标识）',
                                 `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID（逻辑外键）',
                                 `collection_id` BIGINT UNSIGNED NULL COMMENT '收藏列表ID（与收藏列表表关联）',
                                 `total_play_duration` INT UNSIGNED NULL DEFAULT 0 COMMENT '播放时长统计（秒）',
                                 `total_play_count` INT UNSIGNED NULL DEFAULT 0 COMMENT '播放数目统计',
                                 `last_login_time` DATETIME NULL DEFAULT NULL  COMMENT '最后登录时间',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为数据表';