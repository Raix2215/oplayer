package com.huangzizhu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashWithSalt {

    // 生成随机盐值
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 盐值长度为16字节
        random.nextBytes(salt);
        return salt;
    }

    // 对密码进行哈希加盐
    public static byte[] hashPassword(String password, byte[] salt) {
        try {
            // 创建SHA-256的MessageDigest实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 将密码和盐值拼接后进行哈希运算
            digest.update(salt);
            digest.update(password.getBytes());
            return digest.digest(); // 返回哈希值
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("哈希算法不存在", e);
        }
    }
}