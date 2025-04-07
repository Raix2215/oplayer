package com.huangzizhu.service.aop;

import com.huangzizhu.annotion.AdminOrUserCheck;
import com.huangzizhu.exception.PermissionDenyException;
import com.huangzizhu.utils.CurrentHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CheckAdminOrUserAspect {
    @Around("@annotation(check)")
    public Object checkUserPermission(ProceedingJoinPoint joinPoint, AdminOrUserCheck check) throws Throwable {
        if(CurrentHolder.getCurrentIsAdmin()) return joinPoint.proceed();
        // 获取当前用户的id
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (currentUserId == null) {
            throw new PermissionDenyException("用户未登录");
        }
        String userId = currentUserId.toString();
        // 2. 获取方法参数中的userId
        String paramUserId = CheckUserAspect.extractUserIdFromParams(joinPoint.getArgs(), check.field());
        if(!userId.equals(paramUserId)) {
            throw new PermissionDenyException("没有权限访问该资源");
        }
        // 执行目标方法
        return joinPoint.proceed();
    }
}
