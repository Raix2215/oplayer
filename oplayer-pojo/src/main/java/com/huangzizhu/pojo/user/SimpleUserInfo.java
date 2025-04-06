package com.huangzizhu.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简略的用户查询参数，主要用于模糊查询
 * @Author huangzizhu
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserInfo {
    private String username;
    private  Integer id;
}
