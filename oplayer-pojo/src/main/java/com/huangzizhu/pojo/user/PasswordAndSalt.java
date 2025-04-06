package com.huangzizhu.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密码和盐
 * @Author huangzizhu
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordAndSalt {
    private String hashedPassword;
    private String salt;
}
