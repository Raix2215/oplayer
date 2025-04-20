package com.huangzizhu.service;

import com.huangzizhu.pojo.SendEmailParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface OtherService {
    String uploadImg(MultipartFile file);

    BufferedImage getCaptchaImage(String uuid);

    void sendEmail(SendEmailParam param);
}
