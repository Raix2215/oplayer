package com.huangzizhu.mapper;


import com.huangzizhu.pojo.RegParam;
import com.huangzizhu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户持久层
 * @Author huangzizhu
 * @Version 1.0
 */
@Mapper
public interface UserMapper {

    List<User> getAllUser(Integer start, Integer count);

    Integer countAllUser();

    void insertUser(RegParam param);

    User getUser(String username);
}
