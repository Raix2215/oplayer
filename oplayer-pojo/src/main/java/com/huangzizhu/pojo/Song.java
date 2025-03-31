package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

/**
 * 歌曲表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private Long id; // 歌曲ID（唯一标识）
    private String name; // 歌曲名称
    private String artist; // 歌手/艺术家
    private Long albumId; // 专辑ID（逻辑外键）
    private Integer year; // 发行年份
    private Integer duration; // 时长（秒）
    private String format; // 音频格式（如MP3、WAV等）
    private Long size; // 文件大小（字节）
    private String coverUrl; // 封面图片URL
    private Integer bitRate; // 比特率（kbps）
    private Integer sampleRate; // 采样率（Hz）
    private String path; // 存储路径
}