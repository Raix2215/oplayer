package com.huangzizhu.service.impl;

import com.huangzizhu.exception.DuplicateValueException;
import com.huangzizhu.exception.PasswordErrorException;
import com.huangzizhu.exception.UserNotFoundException;
import com.huangzizhu.mapper.AdminMapper;
import com.huangzizhu.pojo.*;
import com.huangzizhu.pojo.user.Admin;
import com.huangzizhu.pojo.user.AdminAddParam;
import com.huangzizhu.pojo.user.LoginParam;
import com.huangzizhu.pojo.user.LoginResult;
import com.huangzizhu.service.AdminService;
import com.huangzizhu.utils.HashWithSalt;
import com.huangzizhu.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void addAdmin(AdminAddParam param) {
        //对密码进行哈希加盐，记录哈希后的密码和盐值
        hashPassword(param);
        //将用户信息插入数据库
        try {
            adminMapper.addAdmin(param);
        }catch (DuplicateKeyException e){
            throw new DuplicateValueException(e.getMessage(),e);
        }
    }

    @Override
    public LoginResult<Admin> login(LoginParam param) {
        //判断用户是否存在
        Admin admin = adminMapper.getAdmin(param.getUsername());
        if(admin == null){
            throw new UserNotFoundException("管理员信息不存在");
        }
        //获取盐值
        String salt = admin.getSalt();
        //salt转换为字节数组
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        //进行哈西后比较
        byte[] passwordToCheck = HashWithSalt.hashPassword(param.getHashedPassword(), saltBytes);
        if(java.util.Arrays.equals(passwordToCheck,Base64.getDecoder().decode(admin.getHashedPassword()) )){
            //登录成功,清除敏感信息，返回token
            admin.setSalt(null);
            admin.setHashedPassword(null);
            String token = JWTUtil.generateToken(admin.getId(), admin.getUsername(),true);
            return new LoginResult<>(admin, token);
        }
        //登陆失败，返回错误信息
        throw new PasswordErrorException("密码错误");
    }

    @Override
    public QueryResult<Admin> getAllAdmin() {
        Integer total = adminMapper.count();
        List<Admin> list = adminMapper.getAllAdmin();
        return new QueryResult<Admin>(total, list);
    }

    private void hashPassword(AdminAddParam param) {
        //生成随机盐值
        byte[] salt = HashWithSalt.generateSalt();
        //对密码进行哈希加盐
        byte[] hashedPassword = HashWithSalt.hashPassword(param.getHashedPassword(), salt);
        //将哈希后的密码和盐值转为Base64字符串
        param.setHashedPassword(Base64.getEncoder().encodeToString(hashedPassword));
        param.setSalt(Base64.getEncoder().encodeToString(salt));
    }
}
