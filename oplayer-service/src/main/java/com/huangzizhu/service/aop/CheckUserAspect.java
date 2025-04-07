package com.huangzizhu.service.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckUserAspect {

    @Before("@annotation(com.huangzizhu.annotion.User)")
    public boolean checkUserPermission()
    {
        return true;
    }
}
