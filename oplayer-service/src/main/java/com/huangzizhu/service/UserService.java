package com.huangzizhu.service;

import com.huangzizhu.pojo.*;

import java.util.List;

/**
 * 用户服务接口
 * @Author huangzizhu
 * @Version 1.0
 */
public interface UserService {
    UserQueryResult getAllUser(UserQueryParam param);

    void regUser(RegParam param);

    LoginResult login(LoginParam param);

    User getUserById(Integer id);

    List<SimpleUserInfo> fuzzySearchUser(String username);

    void updateUserInfo(UpdateUserInfoParam param);
}
