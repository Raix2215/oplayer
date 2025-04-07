package com.huangzizhu.service.aop;

import com.huangzizhu.exception.PermissionDenyException;
import com.huangzizhu.utils.CurrentHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckAdminAspect {

    @Around("@annotation(com.huangzizhu.annotion.AdminCheck)")
    public Object checkAdminPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前用户的权限
        Boolean isAdmin = CurrentHolder.getCurrentIsAdmin();
        if (isAdmin == null) {
            throw new PermissionDenyException("用户未登录");
        }
        if (!isAdmin) {
            throw new PermissionDenyException("没有权限访问该资源");
        }
        // 执行目标方法
        return joinPoint.proceed();
    }
}
