package com.huangzizhu.service.aop;

import com.huangzizhu.annotion.UserCheck;
import com.huangzizhu.exception.PermissionDenyException;
import com.huangzizhu.utils.CurrentHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class CheckUserAspect {

    @Around("@annotation(userCheck)")
    public Object checkUserPermission(ProceedingJoinPoint joinPoint,UserCheck userCheck) throws Throwable {
        // 获取当前用户的id
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (currentUserId == null) {
            throw new PermissionDenyException("用户未登录");
        }
        String userId = currentUserId.toString();
        // 2. 获取方法参数中的userId
        String paramUserId = extractUserIdFromParams(joinPoint.getArgs(), userCheck.field());
        if(!userId.equals(paramUserId)) {
            throw new PermissionDenyException("没有权限访问该资源");
        }
        // 执行目标方法
        return joinPoint.proceed();
    }
    public static String extractUserIdFromParams(Object[] args, String fieldName) {
        for (Object arg : args) {
            try {
                // 使用反射获取参数对象的userId属性
                Field field = arg.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(arg);
                if (value != null) {
                    return value.toString();
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // 忽略没有该字段的参数
            }
        }
        throw new IllegalArgumentException("未找到userId参数");
    }
}
