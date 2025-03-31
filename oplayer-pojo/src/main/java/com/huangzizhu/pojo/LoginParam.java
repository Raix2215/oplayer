package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录参数
 * @Author huangzizhu
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginParam {
    private String username;
    private String hashedPassword;
    private String ip;
    private String salt;
}
