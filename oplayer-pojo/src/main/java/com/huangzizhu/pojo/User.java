package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户基本信息表
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id; // 用户ID（唯一标识）
    private String username; // 用户名
    private String hashedPassword; // 密码（加密存储）
    private String salt;
    private String email; // 邮箱
    private String phone; // 手机号码
    private Integer gender; // 性别（1男，2女，0未知）
    private LocalDate birthDate; // 出生日期
    private String avatarUrl; // 头像URL
    private String description; // 个人签名
    private String background; // 个人空间背景图
    private LocalDateTime regTime; // 注册时间
}