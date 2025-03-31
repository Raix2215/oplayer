CREATE TABLE `user` (
                        `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID（唯一标识）',
                        `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                        `hashed_password` CHAR(64) NOT NULL COMMENT '密码（加密存储）',
                        `salt` CHAR(64) NOT NULL COMMENT '盐值（用于密码加盐）',
                        `email` VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
                        `phone` VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号码',
                        `gender` TINYINT UNSIGNED DEFAULT 0 COMMENT '性别（1男，2女，0未知）',
                        `birth_date` DATE NULL COMMENT '出生日期',
                        `avatar_url` VARCHAR(255) NULL DEFAULT '' COMMENT '头像URL',
                        `description` VARCHAR(255) NULL DEFAULT '' COMMENT '个人签名',
                        `background` VARCHAR(255) NULL DEFAULT '' COMMENT '个人空间背景图',
                        `reg_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';