package com.huangzizhu.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 更新用户信息表单
 * @Author huangzizhu
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoParam {
    private Integer id;
    private String email; // 邮箱
    private String phone; // 手机号码
    private Integer gender; // 性别（1男，2女，0未知）
    private LocalDate birthDate; // 出生日期
    private String avatarUrl; // 头像URL
    private String description; // 个人签名
    private String background; // 个人空间背景图
    private String ip;
}
