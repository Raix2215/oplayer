package com.huangzizhu.service.aop;

import com.huangzizhu.mapper.LogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogMapper logMapper;

    @Around("@annotation(com.huangzizhu.annotion.Log)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis(); // 开始时间
        Object result = joinPoint.proceed(); // 执行目标方法
        long endTime = System.currentTimeMillis(); // 结束时间
        long costTime = endTime - startTime; // 计算耗时

        // 记录日志
        return result;
    }
}
