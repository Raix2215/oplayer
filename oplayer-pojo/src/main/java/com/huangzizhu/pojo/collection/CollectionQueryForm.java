package com.huangzizhu.pojo.collection;

import com.huangzizhu.pojo.QueryForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionQueryForm extends QueryForm {
    private Integer userId;
    private String ip;
}
