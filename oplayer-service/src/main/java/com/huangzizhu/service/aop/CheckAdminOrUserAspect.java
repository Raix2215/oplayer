package com.huangzizhu.service.aop;

import com.huangzizhu.annotion.AdminOrUserCheck;
import com.huangzizhu.exception.PermissionDenyException;
import com.huangzizhu.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class CheckAdminOrUserAspect {
    @Around("@annotation(check)")
    public Object checkUserPermission(ProceedingJoinPoint joinPoint, AdminOrUserCheck check) throws Throwable {
        log.info("进入用户或管理员权限校验切面");
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
        log.info("权限校验通过");
        // 执行目标方法
        return joinPoint.proceed();
    }
}
