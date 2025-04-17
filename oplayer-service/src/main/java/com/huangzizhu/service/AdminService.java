package com.huangzizhu.service;

import com.huangzizhu.pojo.*;
import com.huangzizhu.pojo.user.Admin;
import com.huangzizhu.pojo.user.AdminAddParam;
import com.huangzizhu.pojo.user.LoginParam;
import com.huangzizhu.pojo.user.LoginResult;

/**
 * 管理员服务接口
 * @Author huangzizhu
 * @Version 1.0
 */
public interface AdminService {

    void addAdmin(AdminAddParam param);

    LoginResult<Admin> login(LoginParam param);

    QueryResult<Admin> getAllAdmin();
}
