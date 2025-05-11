package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Live {
    private String province;           // 省份
    private String city;               // 城市
    private String adcode;            // 区域编码
    private String weather;            // 天气现象
    private String temperature;        // 温度
    private String winddirection;      // 风向
    private String windpower;          // 风力
    private String humidity;           // 湿度
    private String reporttime;         // 数据发布时间
    private String temperature_float;  // 温度(浮点数)
    private String humidity_float;     // 湿度(浮点数)
}