package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//每日一言
public class Says {
    private Integer id;
    private String sentence;
    private String fromSource;
    private LocalDateTime addTime;
}
