package com.huangzizhu.service;


import com.huangzizhu.exception.EmailException;
import com.huangzizhu.utils.AliEmailSender;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailCaptchaManager {
    // 存储邮箱、验证码和生成时间
    private static final ConcurrentHashMap<String, CodeInfo> codeMap = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static volatile EmailCaptchaManager instance;

    // 验证码有效期（单位：分钟）
    private static final long CODE_EXPIRATION_TIME = 5;

    private EmailCaptchaManager() {
        // 初始化定时任务，每分钟清理一次过期验证码
        scheduler.scheduleAtFixedRate(this::cleanExpiredCodes, 0, 1, TimeUnit.MINUTES);
    }

    public static EmailCaptchaManager getInstance() {
        if (instance == null) {
            synchronized (EmailCaptchaManager.class) {
                if (instance == null) {
                    instance = new EmailCaptchaManager();
                }
            }
        }
        return instance;
    }

    // 生成并存储验证码
    public void generateAndStoreCode(String email, String code) {
        codeMap.put(email, new CodeInfo(code, System.currentTimeMillis()));
        String body = String.format("%s:您的验证码是 %s ,请妥善保管，有效时长5分钟。",email,code);
        try {
            AliEmailSender.send(email, body);
        } catch (Exception e) {
            throw new EmailException("发送邮件失败");
        }
    }

    // 验证验证码
    public boolean verifyCode(String email, String code) {
        CodeInfo codeInfo = codeMap.get(email);
        if (codeInfo != null && codeInfo.getCode().equals(code)) {
            // 验证成功后移除验证码
            codeMap.remove(email);
            return true;
        }
        return false;
    }

    // 清理过期验证码
    private void cleanExpiredCodes() {
        long currentTime = System.currentTimeMillis();
        codeMap.forEach((email, codeInfo) -> {
            if (currentTime - codeInfo.getCreateTime() > CODE_EXPIRATION_TIME * 60 * 1000) {
                codeMap.remove(email);
            }
        });
    }

    // 内部类，存储验证码和生成时间
    private static class CodeInfo {
        private final String code;
        private final long createTime;

        public CodeInfo(String code, long createTime) {
            this.code = code;
            this.createTime = createTime;
        }

        public String getCode() {
            return code;
        }

        public long getCreateTime() {
            return createTime;
        }
    }
}
