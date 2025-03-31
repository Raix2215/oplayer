package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

/**
 * 歌曲审核表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongAudit extends AuditBase {
    private Song song;
}