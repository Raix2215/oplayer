package com.huangzizhu.service.impl;

import com.huangzizhu.mapper.UserMapper;
import com.huangzizhu.pojo.*;
import com.huangzizhu.service.UserService;
import com.huangzizhu.utils.HashWithSalt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;


/**
 * 用户服务实现类
 * @Author huangzizhu
 * @Version 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserQueryResult getAllUser(UserQueryParam param) {
        Integer total = userMapper.countAllUser();
        Integer start = (param.getPage() - 1) * param.getPageSize();
        List<User> list = userMapper.getAllUser(start, param.getPageSize());
        return new UserQueryResult(total, list);
    }

    @Override
    public void regUser(RegParam param) {
        //对密码进行哈希加盐，记录哈希后的密码和盐值
        hashPassword(param);
        //将用户信息插入数据库
        userMapper.insertUser(param);


    }

    @Override
    public LoginResult login(LoginParam param) {
        //判断用户是否存在
        User user = userMapper.getUser(param.getUsername());
        log.error("user:{}", user);
        //获取salt
        String salt = user.getSalt();
        return null;
    }

    private void hashPassword(RegParam param) {
        //生成随机盐值
        byte[] salt = HashWithSalt.generateSalt();
        //对密码进行哈希加盐
        byte[] hashedPassword = HashWithSalt.hashPassword(param.getHashedPassword(), salt);
        //将哈希后的密码和盐值转为Base64字符串
        param.setHashedPassword(Base64.getEncoder().encodeToString(hashedPassword));
        param.setSalt(Base64.getEncoder().encodeToString(salt));
    }
}
