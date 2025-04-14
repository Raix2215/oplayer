package com.huangzizhu.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface OtherService {
    String uploadImg(MultipartFile file);

    BufferedImage getCaptchaImage(String uuid);
}
