package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户查询结果
 * @Author huangzizhu
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryResult {
    private Integer total;
    private List<User> list;
}
