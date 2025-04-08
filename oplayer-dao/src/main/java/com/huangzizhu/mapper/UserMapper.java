package com.huangzizhu.mapper;


import com.huangzizhu.pojo.user.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
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

    void insertUser(UserRegParam param);

    User getUser(String username);

    User getUserById(Integer id);

    List<SimpleUserInfo> fuzzySearchUser(String username);

    Integer updateUser(UpdateUserInfoParam param);

    Integer deleteUser(Integer id);

    Integer updatePassword(LoginParam param);

    void addUserBehavior(UserBehavior userBehavior);

    void setLastLoginTime(Integer userId, LocalDateTime lastLoginTime);
}
