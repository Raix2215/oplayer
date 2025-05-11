package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {
    private String status;      // 状态码
    private String count;       // 结果数量
    private String info;        // 状态信息
    private String infocode;    // 信息代码
    private List<Live> lives;   // 实时天气信息列表
}