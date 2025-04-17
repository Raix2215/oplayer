package com.huangzizhu.mapper;

import com.huangzizhu.pojo.user.Admin;
import com.huangzizhu.pojo.user.AdminAddParam;

import java.util.List;

public interface AdminMapper {
    void addAdmin(AdminAddParam param);

    Admin getAdmin(String username);

    Admin checkAdmin(Integer id, String username);

    Integer count();

    List<Admin> getAllAdmin();
}
