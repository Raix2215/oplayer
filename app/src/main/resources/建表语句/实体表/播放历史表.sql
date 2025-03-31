CREATE TABLE `play_history` (
                                `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '播放历史ID（唯一标识）',
                                `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID（逻辑外键，关联到用户表）',
                                `song_id` BIGINT UNSIGNED NOT NULL COMMENT '歌曲ID（逻辑外键，关联到歌曲表）',
                                `progress` TINYINT UNSIGNED NULL DEFAULT 0 COMMENT '播放进度（百分比）',
                                `ip` VARCHAR(50) NULL DEFAULT '' COMMENT 'IP地址',
                                `play_mode` TINYINT UNSIGNED NULL DEFAULT 1 COMMENT '播放模式（1单曲循环、2列表循环、3随机播放等）',
                                `volume` TINYINT UNSIGNED NULL DEFAULT 50 COMMENT '音量设置（百分比）',
                                `is_paused` TINYINT UNSIGNED NULL DEFAULT 0 COMMENT '是否暂停（0未暂停，1已暂停）',
                                `play_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '播放时间',
                                PRIMARY KEY (`id`),
                                KEY `idx_user_id` (`user_id`),
                                KEY `idx_song_id` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='播放历史表';