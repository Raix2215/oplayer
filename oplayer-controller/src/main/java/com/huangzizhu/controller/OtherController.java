package com.huangzizhu.controller;

import com.huangzizhu.pojo.Result;
import com.huangzizhu.pojo.Says;
import com.huangzizhu.pojo.SendEmailParam;
import com.huangzizhu.pojo.WeatherInfo;
import com.huangzizhu.service.OtherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController()
public class OtherController {
    @Autowired
    private OtherService otherService;

    @PostMapping("/upload/img")
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        log.info("上传图片{}",file.getOriginalFilename());
        String url = otherService.uploadImg(file);
        return Result.success(url);
    }
    @PostMapping("/tool/captcha")
    public void getCaptchaImage(String uuid, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        BufferedImage image = otherService.getCaptchaImage(uuid);
        try (OutputStream os = response.getOutputStream()) {
            ImageIO.write(image, "png", os);
        }
    }
    @PostMapping("/tool/sendEmail")
    public Result sendEmail(@RequestBody SendEmailParam param) {
        log.info("发送验证码到邮箱{}",param);
        otherService.sendEmail(param);
        return Result.success();
    }
    //每日一言
    @GetMapping("/tool/says")
    public Result getSays() {
        log.info("获取每日一言");
        Says says = otherService.getSays();
        return Result.success(says);
    }
    //获取天气
    @GetMapping("/weather/{ip}")
    public Result getWeather(@PathVariable String ip){
        log.info("获取天气信息");
        WeatherInfo data = otherService.getWeather(ip);
        return Result.success(data);
    }

}
