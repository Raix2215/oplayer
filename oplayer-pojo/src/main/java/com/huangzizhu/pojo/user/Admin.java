package com.huangzizhu.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员表
 * @Author： huangzizhu
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends PasswordAndSalt {
    private Integer id; // 管理员ID（唯一标识）
    private String username; // 用户名
    private String email; // 邮箱
    private String phone; // 手机号码
    private String name; // 姓名
    private String avatarUrl; // 头像URL
}