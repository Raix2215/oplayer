CREATE TABLE `song_audit` (
                              `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '审核ID（唯一标识）',
                              `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '审核状态（1待审核、2已通过、3已拒绝）',
                              `time` DATETIME NULL DEFAULT NULL COMMENT '审核时间',
                              `auditor_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '审核人ID（逻辑外键，关联到管理员表）',

                              `song_name` VARCHAR(255) NOT NULL COMMENT '歌曲名称',
                              `artist` VARCHAR(255) NOT NULL COMMENT '歌手/艺术家',
                              `album_id` BIGINT UNSIGNED NOT NULL COMMENT '专辑ID',
                              `release_year` YEAR NULL COMMENT '发行年份',
                              `duration` INT UNSIGNED NOT NULL COMMENT '时长（秒）',
                              `audio_format` VARCHAR(50) NOT NULL COMMENT '音频格式（如MP3、WAV等）',
                              `file_size` BIGINT UNSIGNED NOT NULL COMMENT '文件大小（字节）',
                              `cover_image_url` VARCHAR(255) NULL DEFAULT '' COMMENT '封面图片URL',
                              `bit_rate` INT UNSIGNED NULL COMMENT '比特率（kbps）',
                              `sample_rate` INT UNSIGNED NULL COMMENT '采样率（Hz）',
                              `storage_path` VARCHAR(255) NOT NULL COMMENT '存储路径',

                              `approval_time` DATETIME NULL DEFAULT NULL COMMENT '审核通过时间',
                              `rejection_reason` TEXT NULL DEFAULT NULL COMMENT '审核拒绝原因',
                              `modification_suggestions` TEXT NULL DEFAULT NULL COMMENT '审核修改建议',

                              `priority` TINYINT UNSIGNED NOT NULL DEFAULT 2 COMMENT '审核优先级（1高、2中、3低）',

                              PRIMARY KEY (`id`),
                              KEY `idx_audit_status` (`status`),
                              KEY `idx_priority` (`priority`),
                              KEY `idx_auditor_id` (`auditor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌曲审核表';