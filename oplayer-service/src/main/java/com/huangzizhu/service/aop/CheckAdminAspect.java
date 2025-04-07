package com.huangzizhu.service.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckAdminAspect {

    @Before("@annotation(com.huangzizhu.annotion.Admin)")
    public boolean checkAdminPermission() {
        return true;
    }
}
