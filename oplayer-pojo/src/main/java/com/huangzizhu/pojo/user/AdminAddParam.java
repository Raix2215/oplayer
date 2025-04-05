package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员添加参数
 * @Author huangzizhu
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddParam extends Admin{
    private String ip;
}
