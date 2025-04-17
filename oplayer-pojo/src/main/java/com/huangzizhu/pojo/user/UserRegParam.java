package com.huangzizhu.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册参数
 * @Author huangzizhu
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegParam extends User {
    private String ip;
}
