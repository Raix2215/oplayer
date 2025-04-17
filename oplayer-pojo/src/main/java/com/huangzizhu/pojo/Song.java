package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Objects;

/**
 * 歌曲表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private Integer id; // 歌曲ID（唯一标识）
    private String name; // 歌曲名称
    private String artist; // 歌手/艺术家
    private Integer albumId; // 专辑ID（逻辑外键）
    private String albumName; // 专辑名称
    private String year; // 发行年份
    private Integer duration; // 时长（秒）
    private String format; // 音频格式（如MP3、WAV等）
    private Integer size; // 文件大小（字节）
    private String coverUrl; // 封面图片URL
    private Integer bitRate; // 比特率（kbps）
    private Integer sampleRate; // 采样率（Hz）
    private String path; // 存储路径
    private String md5; // 文件的MD5值，用于唯一标识
    private Boolean isAvailable; // 是否可用（true表示可用，false表示不可用）

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(md5, song.md5);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(md5);
    }
}