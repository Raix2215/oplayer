package com.huangzizhu.service.aop;

import com.huangzizhu.annotion.UserCheck;
import com.huangzizhu.exception.ParamInvalidException;
import com.huangzizhu.exception.PermissionDenyException;
import com.huangzizhu.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Aspect
@Component
public class CheckUserAspect {

    @Around("@annotation(userCheck)")
    public Object checkUserPermission(ProceedingJoinPoint joinPoint, UserCheck userCheck) throws Throwable {
        log.info("进入用户权限检查切面");
        // 获取当前用户的id
        Integer currentUserId = CurrentHolder.getCurrentId();
        if (currentUserId == null) {
            throw new PermissionDenyException("用户未登录");
        }
        String userId = currentUserId.toString();
        // 2. 获取方法参数中的userId
        String paramUserId = extractUserIdFromParams(joinPoint.getArgs(), userCheck.field());
        if (!userId.equals(paramUserId)) {
            throw new PermissionDenyException("没有权限访问该资源");
        }
        log.info("用户权限检查通过");
        // 执行目标方法
        return joinPoint.proceed();
    }

    public static String extractUserIdFromParams(Object[] args, String fieldName) {
        for (Object arg : args) {
            try {
                // 从当前类及其父类中查找字段
                Field field = findField(arg.getClass(), fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    Object value = field.get(arg);
                    if (value != null) {
                        return value.toString();
                    }
                }
            } catch (IllegalAccessException e) {
                // 忽略无法访问的字段
            }
        }
        throw new ParamInvalidException("未找到userId参数");
    }

    private static Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}
