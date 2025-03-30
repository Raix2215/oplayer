CREATE TABLE `tag` (
                       `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签ID（唯一标识）',
                       `name` VARCHAR(255) NOT NULL COMMENT '标签名称',
                       `description` TEXT NULL COMMENT '标签描述',
                       `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '标签创建时间',
                       `update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '标签更新时间',
                       `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '标签状态（1启用，0禁用）',
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';