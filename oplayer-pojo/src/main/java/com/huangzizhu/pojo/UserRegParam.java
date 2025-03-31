package com.huangzizhu.pojo;

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
public class RegParam {
    private String username;
    private String hashedPassword;
    private String phone;
    private String email;
    private String salt;
    private String ip;
}
