package com.huangzizhu.controller;

import com.huangzizhu.pojo.Result;
import com.huangzizhu.service.OtherService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
public class OtherController {
    @Autowired
    private OtherService otherService;

    @PostMapping("/upload/img")
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        log.info("上传图片{}",file.getOriginalFilename());
        String url = otherService.uploadImg(file);
        return Result.success(url);
    }
    @PostMapping("/captcha")
    public void getCaptchaImage(String uuid, HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("image/png");

        BufferedImage image = otherService.getCaptchaImage(uuid);

        try (OutputStream os = response.getOutputStream()) {
            ImageIO.write(image, "png", os);
        }
    }
}
