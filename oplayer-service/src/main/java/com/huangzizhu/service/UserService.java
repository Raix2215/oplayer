package com.huangzizhu.service;

import com.huangzizhu.pojo.*;

/**
 * 用户服务接口
 * @Author huangzizhu
 * @Version 1.0
 */
public interface UserService {
    UserQueryResult getAllUser(UserQueryParam param);

    void regUser(RegParam param);

    LoginResult login(LoginParam param);
}
