CREATE TABLE `tag_audit` (
                             `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '审核ID（唯一标识）',
                             `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '审核状态（1待审核、2已通过、3已拒绝）',
                             `audit_time` DATETIME NULL DEFAULT NULL COMMENT '审核时间',
                             `auditor_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '审核人ID（逻辑外键，关联到管理员表）',
                             `approval_time` DATETIME NULL DEFAULT NULL COMMENT '审核通过时间',
                             `rejection_reason` TEXT NULL DEFAULT NULL COMMENT '审核拒绝原因',
                             `modification_suggestions` TEXT NULL DEFAULT NULL COMMENT '审核修改建议',
                             `priority` TINYINT UNSIGNED NOT NULL DEFAULT 2 COMMENT '审核优先级（1高、2中、3低）',
                             `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID（逻辑外键，关联到用户表）',

                             `tag_name` VARCHAR(255) NOT NULL COMMENT '标签名称',
                             `tag_description` TEXT NULL COMMENT '标签描述',
                             `tag_status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '标签状态（1启用、2禁用等）',

                             PRIMARY KEY (`id`),
                             KEY `idx_audit_status` (`status`),
                             KEY `idx_priority` (`priority`),
                             KEY `idx_auditor_id` (`auditor_id`),
                             KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新增标签审核表';