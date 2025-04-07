package com.huangzizhu.pojo.music;

import com.huangzizhu.pojo.QueryForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicQueryForm extends QueryForm {
    private Integer tagId;
    private String name;
    private String artist;
    private String ip;
}
