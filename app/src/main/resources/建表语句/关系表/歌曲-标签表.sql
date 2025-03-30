CREATE TABLE `song_tag` (
    `relation_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关系ID（唯一标识）',
    `song_id` BIGINT UNSIGNED NOT NULL COMMENT '歌曲ID（逻辑外键，关联到歌曲表）',
    `tag_id` BIGINT UNSIGNED NOT NULL COMMENT '标签ID（逻辑外键，关联到标签表）',
    PRIMARY KEY (`relation_id`),
    KEY `idx_song_id` (`song_id`),
    KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌曲与标签关系表';