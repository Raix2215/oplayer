package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 歌曲打标签审核表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongTagAudit extends AuditBase {
    private Long tagId; // 标签ID（逻辑外键，关联到标签表）
    private Long songId; // 歌曲ID（逻辑外键，关联到歌曲表）
}