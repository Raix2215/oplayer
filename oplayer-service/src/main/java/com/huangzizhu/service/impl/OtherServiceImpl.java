package com.huangzizhu.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.huangzizhu.exception.FIleException;
import com.huangzizhu.service.OtherService;
import com.huangzizhu.utils.AliOSSOperator;
import com.huangzizhu.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class OtherServiceImpl implements OtherService {
    @Autowired AliOSSOperator aliOSSOperator;
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
}
