package com.huangzizhu.pojo.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 标签表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private Integer id; // 标签ID（唯一标识）
    private Integer categoryId; // 标签分类ID（外键）
    private String name; // 标签名称
    private String description; // 标签描述
    private LocalDateTime createTime; // 标签创建时间
    private LocalDateTime updateTime; // 标签更新时间
    private Integer status; // 标签状态（1启用，0禁用）
}