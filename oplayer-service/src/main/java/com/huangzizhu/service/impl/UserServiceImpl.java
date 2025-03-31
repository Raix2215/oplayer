package com.huangzizhu.service.impl;

import com.huangzizhu.exception.DuplicateValueException;
import com.huangzizhu.exception.PasswordErrorException;
import com.huangzizhu.exception.UserNotFoundException;
import com.huangzizhu.mapper.UserMapper;
import com.huangzizhu.pojo.*;
import com.huangzizhu.service.UserService;
import com.huangzizhu.utils.HashWithSalt;
import com.huangzizhu.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        try {
            userMapper.insertUser(param);
        }catch (DuplicateKeyException e){
            throw new DuplicateValueException(e.getMessage(),e);
        }
    }

    @Override
    public LoginResult login(LoginParam param) {
        //判断用户是否存在
        User user = userMapper.getUser(param.getUsername());
        if(user == null){
            throw new UserNotFoundException("用户不存在");
        }
        //获取盐值
        String salt = user.getSalt();
        //salt转换为字节数组
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        //进行哈西后比较
        byte[] passwordToCheck = HashWithSalt.hashPassword(param.getHashedPassword(), saltBytes);
        if(java.util.Arrays.equals(passwordToCheck,Base64.getDecoder().decode(user.getHashedPassword()) )){
            //登录成功,清除敏感信息，返回token
            user.setSalt(null);
            user.setHashedPassword(null);
            String token = JWTUtil.generateToken(param.getUsername());
            return new LoginResult(user, token);
        }
        //登陆失败，返回错误信息
        throw new PasswordErrorException("密码错误");
    }

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        if(user == null){
            throw new UserNotFoundException("用户不存在，检查id是否正确");
        }
        return user;
    }

    @Override
    public List<SimpleUserInfo> fuzzySearchUser(String username) {
        return userMapper.fuzzySearchUser(username);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoParam param) {
        userMapper.updateeUser(param);
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
