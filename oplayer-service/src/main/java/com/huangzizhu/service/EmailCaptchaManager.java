package com.huangzizhu.service;

import com.huangzizhu.exception.CaptchaException;
import com.huangzizhu.exception.EmailException;
import com.huangzizhu.utils.AliEmailSender;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class EmailCaptchaManager {
    // 存储邮箱、验证码和生成时间
    private static final ConcurrentHashMap<String, CodeInfo> codeMap = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static volatile EmailCaptchaManager instance;

    // 使用AtomicInteger保证线程安全
    private static final AtomicInteger dailySendCount = new AtomicInteger(0);
    private static final int MAX_DAILY_SEND_COUNT = 200; // 24小时内最大发送次数
    private static final long DAILY_RESET_INTERVAL = 24 * 60 * 60 * 1000L; // 24小时重置周期

    // 验证码有效期（单位：分钟）
    private static final long CODE_EXPIRATION_TIME = 5;

    private EmailCaptchaManager() {
        // 初始化定时任务，每分钟清理一次过期验证码
        scheduler.scheduleAtFixedRate(this::cleanExpiredCodes, 0, CODE_EXPIRATION_TIME, TimeUnit.MINUTES);
        // 初始化24小时重置计数器任务
        scheduler.scheduleAtFixedRate(this::resetDailyCount, DAILY_RESET_INTERVAL, DAILY_RESET_INTERVAL, TimeUnit.MILLISECONDS);
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
        // 检查是否超过每日发送限制
        if (dailySendCount.get() >= MAX_DAILY_SEND_COUNT) {
            throw new EmailException("今日邮件发送量已达上限，请明天再试");
        }

        codeMap.put(email, new CodeInfo(code, System.currentTimeMillis()));
        String body = String.format("%s:您的验证码是 %s ,请妥善保管，有效时长5分钟。", email, code);
        try {
            AliEmailSender.send(email, body);
            // 发送成功后增加计数
            dailySendCount.incrementAndGet();
            log.info("今日已发送邮件数量: {}", dailySendCount.get());
        } catch (Exception e) {
            throw new EmailException("发送邮件失败",e);
        }
    }

    // 验证验证码
    public boolean verifyCode(String email, String code) {
        CodeInfo codeInfo = null;
        try {
            codeInfo = codeMap.get(email);
        } catch (Exception e) {
            throw new CaptchaException("验证码已过期", e);
        }
        return codeInfo != null && codeInfo.getCode().equals(code);
    }

    // 清理过期验证码
    private void cleanExpiredCodes() {
        log.info("邮箱验证码清理定时任务");
        long currentTime = System.currentTimeMillis();
        codeMap.forEach((email, codeInfo) -> {
            if (currentTime - codeInfo.getCreateTime() > CODE_EXPIRATION_TIME * 60 * 1000) {
                codeMap.remove(email);
            }
        });
    }

    // 重置每日发送计数器
    private void resetDailyCount() {
        dailySendCount.set(0);
        log.info("每日邮件发送计数器已重置");
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