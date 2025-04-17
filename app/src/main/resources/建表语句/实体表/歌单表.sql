CREATE TABLE `playlist` (
                            `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '歌单ID（唯一标识）',
                            `name` VARCHAR(255) NOT NULL COMMENT '歌单名称',
                            `creator_id` BIGINT UNSIGNED NOT NULL COMMENT '创建者ID（逻辑外键，关联到用户表）',
                            `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `description` VARCHAR(255) NULL DEFAULT '' COMMENT '歌单描述',
                            `cover_url` VARCHAR(255) NULL COMMENT '歌单封面图片URL',
                            `total` INT UNSIGNED NULL DEFAULT 0 COMMENT '歌单歌曲数量',
                            `total_duration` INT UNSIGNED NULL DEFAULT 0 COMMENT '歌单时长（总时长，秒）',
                            PRIMARY KEY (`id`),
                            KEY `idx_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌单表';