CREATE TABLE `collection_song` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关系ID（唯一标识）',
    `collection_id` BIGINT UNSIGNED NOT NULL COMMENT '收藏ID（逻辑外键，关联到收藏表）',
    `song_id` BIGINT UNSIGNED NOT NULL COMMENT '歌曲ID（逻辑外键，关联到歌曲表）',
    `collection_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_playlist_song (collection_id, song_id),
    KEY `idx_collection_id` (`collection_id`),
    KEY `idx_song_id` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌曲与收藏关系表';