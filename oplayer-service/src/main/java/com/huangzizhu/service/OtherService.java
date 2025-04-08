package com.huangzizhu.service;

import org.springframework.web.multipart.MultipartFile;

public interface OtherService {
    String uploadImg(MultipartFile file);
}
