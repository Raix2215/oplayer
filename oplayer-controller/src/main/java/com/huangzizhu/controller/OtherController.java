package com.huangzizhu.controller;

import com.huangzizhu.pojo.Result;
import com.huangzizhu.service.OtherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
