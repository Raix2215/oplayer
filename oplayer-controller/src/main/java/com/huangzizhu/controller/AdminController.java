package com.huangzizhu.controller;

import com.huangzizhu.pojo.*;
import com.huangzizhu.pojo.user.Admin;
import com.huangzizhu.pojo.user.AdminAddParam;
import com.huangzizhu.pojo.user.LoginParam;
import com.huangzizhu.pojo.user.LoginResult;
import com.huangzizhu.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Result addAdmin(@RequestBody AdminAddParam param) {
        log.info("添加管理员 param:{}", param);
        adminService.addAdmin(param);
        return Result.success();
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginParam param) {
        log.info("管理员登录 param:{}", param);
        LoginResult<Admin> data = adminService.login(param);
        return Result.success(data);
    }
    @GetMapping("all")
    public Result getAllAdmin() {
        log.info("查询所有管理员");
        QueryResult<Admin> data = adminService.getAllAdmin();
        return Result.success(data);
    }



}
