CREATE TABLE `song` (
                        `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '歌曲ID（唯一标识）',
                        `name` VARCHAR(255) NOT NULL COMMENT '歌曲名称',
                        `artist` VARCHAR(255) NOT NULL COMMENT '歌手/艺术家',
                        `album_id` BIGINT UNSIGNED NOT NULL COMMENT '专辑ID（逻辑外键）',
                        `year` YEAR NULL COMMENT '发行年份',
                        `duration` INT UNSIGNED NOT NULL COMMENT '时长（秒）',
                        `format` VARCHAR(50) NOT NULL COMMENT '音频格式（如MP3、WAV等）',
                        `size` BIGINT UNSIGNED NOT NULL COMMENT '文件大小（字节）',
                        `cover_url` VARCHAR(255) NULL DEFAULT '' COMMENT '封面图片URL',
                        `bit_rate` INT UNSIGNED NULL COMMENT '比特率（kbps）',
                        `sample_rate` INT UNSIGNED NULL COMMENT '采样率（Hz）',
                        `path` VARCHAR(255) NOT NULL COMMENT '存储路径',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='歌曲表';