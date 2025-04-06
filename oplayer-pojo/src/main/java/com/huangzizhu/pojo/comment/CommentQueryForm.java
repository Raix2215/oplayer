package com.huangzizhu.pojo.comment;

import com.huangzizhu.pojo.QueryForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentQueryForm extends QueryForm {
    private Integer songId; // 歌曲ID
    private Integer userId; // 用户ID
    private String ip; // IP
}
