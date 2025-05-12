package com.huangzizhu.service;

import com.huangzizhu.pojo.*;
import com.huangzizhu.pojo.user.*;

import java.util.List;

/**
 * 用户服务接口
 * @Author huangzizhu
 * @Version 1.0
 */
public interface UserService {
    QueryResult<User> getAllUser(UserQueryParam param);

    void regUser(UserRegParam param);

    LoginResult<User> login(LoginParam param);

    User getUserById(Integer id);

    List<SimpleUserInfo> fuzzySearchUser(String username);

    void updateUserInfo(UpdateUserInfoParam param);

    void deleteUser(Integer id);

    void updatePassword(LoginParam param);

    User getUserStatus();
}
