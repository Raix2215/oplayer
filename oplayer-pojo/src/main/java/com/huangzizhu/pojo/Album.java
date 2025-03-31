package com.huangzizhu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专辑实体类
 * @Author: huangzizhu
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
   private Long albumId;
   private String albumName;
}
