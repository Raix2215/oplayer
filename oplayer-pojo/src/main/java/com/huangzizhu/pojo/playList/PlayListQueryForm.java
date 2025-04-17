package com.huangzizhu.pojo.playList;

import com.huangzizhu.pojo.QueryForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayListQueryForm extends QueryForm {
    private Integer userId; // 用户ID
    private Integer playListId; // 歌单ID
    private String ip; // IP
}
