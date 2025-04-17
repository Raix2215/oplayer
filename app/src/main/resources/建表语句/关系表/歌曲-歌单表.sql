CREATE TABLE `playlist_song` (
                                 `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关系ID（唯一标识）',
                                 `playlist_id` BIGINT UNSIGNED NOT NULL COMMENT '歌单ID（逻辑外键，关联到歌单表）',
                                 `song_id` BIGINT UNSIGNED NOT NULL COMMENT '歌曲ID（逻辑外键，关联到歌曲表）',
                                 `add_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '歌曲添加到歌单的时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY uk_playlist_song (playlist_id, song_id),
                                 KEY `idx_playlist_id` (`playlist_id`),
                                 KEY `idx_song_id` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌曲与歌单关系表';