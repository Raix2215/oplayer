package com.huangzizhu.pojo.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagForSongParam {
    private Integer tagId;
    private Integer songId;
    private String ip;
}
