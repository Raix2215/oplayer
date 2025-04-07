package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLog {
    private Integer id; // ID
    private Integer operateId; // 操作人ID
    private Integer operateType; // 操作人类型 0未知 2管理员 1普通用户
    private String ip; // 操作IP地址
    private LocalDateTime operateTime; // 操作时间
    private String className; // 操作类名
    private String methodName; // 操作方法名
    private String methodParams; // 操作方法参数(JSON)
    private String returnValue; // 操作方法返回值(JSON)
    private Long costTime; // 操作耗时(毫秒)
}