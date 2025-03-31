CREATE TABLE `collection` (
                              `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '收藏ID（唯一标识）',
                              `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID（逻辑外键，关联到用户表）',
                              `song_id` BIGINT UNSIGNED NOT NULL COMMENT '歌曲ID（逻辑外键，关联到歌曲表）',
                              `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `description` TEXT NULL COMMENT '描述',
                              `cover_url` VARCHAR(255) NULL DEFAULT '' COMMENT '封面图片URL',
                              `duration` INT UNSIGNED NULL DEFAULT 0 COMMENT '时长（总时长，秒）',
                              PRIMARY KEY (`id`),
                              KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';