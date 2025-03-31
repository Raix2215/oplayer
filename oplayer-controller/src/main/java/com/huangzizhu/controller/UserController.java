package com.huangzizhu.controller;

import com.huangzizhu.pojo.*;
import com.huangzizhu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户控制器
 * @Author huangzizhu
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/all")
    public Result getUser(@RequestBody UserQueryParam param) {
        log.info("查询所有用户 param:{}", param);
        UserQueryResult data = userService.getAllUser(param);
        return Result.success(data);
    }
    @PostMapping("/reg")
    public Result regUser(@RequestBody RegParam param) {
        log.info("注册用户 param:{}", param);
        userService.regUser(param);
        return Result.success();
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginParam param) {
        log.info("用户登录 param:{}", param);
        LoginResult data = userService.login(param);
        return Result.success(data);
    }

    @GetMapping("/{id}")
    public Result getUserByid(@PathVariable Integer id) {
        log.info("查询用户 id:{}", id);
        User data = userService.getUserById(id);
        return Result.success(data);
    }

    @GetMapping("/search")
    public Result fuzzySearchUser(@RequestParam String username) {
        log.info("查询用户 username:{}", username);
        List<SimpleUserInfo> data = userService.fuzzySearchUser(username);
        return Result.success(data);
    }

    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody UpdateUserInfoParam param) {
        log.info("更新用户信息 param:{}", param);
        userService.updateUserInfo(param);
        return Result.success();
    }

}
