package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeaMusicToListParam {
    private Integer songId;
    private Integer listId;
    private Integer userId;
    private String ip;
}
