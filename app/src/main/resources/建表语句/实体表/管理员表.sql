CREATE TABLE `admin` (
                         `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员ID（唯一标识）',
                         `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                         `password` CHAR(64) NOT NULL COMMENT '密码（加密存储）',
                         `email` VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
                         `phone` VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号码',
                         `name` VARCHAR(50) NULL DEFAULT '' COMMENT '姓名',
                         `avatar_url` VARCHAR(255) NULL DEFAULT '' COMMENT '头像URL',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';