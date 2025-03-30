CREATE TABLE `comment` (
                           `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论ID（唯一标识）',
                           `song_id` BIGINT UNSIGNED NOT NULL COMMENT '歌曲ID（逻辑外键，关联到歌曲表）',
                           `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID（逻辑外键，关联到用户表）',
                           `txt` TEXT NULL DEFAULT NULL COMMENT '评论内容',
                           `time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
                           `count` INT UNSIGNED NULL DEFAULT 0 COMMENT '点赞数',
                           `ip` VARCHAR(50) NULL DEFAULT '' COMMENT '评论IP',
                           PRIMARY KEY (`id`),
                           KEY `idx_song_id` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';