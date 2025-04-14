package com.huangzizhu.service.impl;

import com.google.code.kaptcha.Producer;
import com.huangzizhu.exception.FIleException;
import com.huangzizhu.service.ImageCaptchaManager;
import com.huangzizhu.service.OtherService;
import com.huangzizhu.utils.AliOSSOperator;
import com.huangzizhu.utils.CommonUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.UUID;

@Service
public class OtherServiceImpl implements OtherService {
    @Autowired AliOSSOperator aliOSSOperator;
    @Autowired
    Producer kaptchaProducer;
    @Override
    public String uploadImg(MultipartFile file) {
        if (file.isEmpty()) throw new FIleException("文件不能为空!");
        if(CommonUtils.isBlank(file.getOriginalFilename())) throw new FIleException("文件名不能为空!");
        try {
            return aliOSSOperator.uploadFile(file.getBytes(), UUID.randomUUID() + file.getOriginalFilename());
        } catch (Exception e){
            throw new FIleException("上传图片失败",e);
        }
    }

    @Override
    public BufferedImage getCaptchaImage(String uuid) {
        String captchaText = kaptchaProducer.createText();
        ImageCaptchaManager.getInstance().generateAndStoreCode(uuid, captchaText);
        return kaptchaProducer.createImage(captchaText);
    }
}
