package com.huangzizhu.pojo.history;

import com.huangzizhu.pojo.QueryForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryQueryParam extends QueryForm {
    private Integer userId;
}
