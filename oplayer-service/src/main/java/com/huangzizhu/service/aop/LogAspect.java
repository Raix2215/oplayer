package com.huangzizhu.service.aop;

import com.huangzizhu.mapper.LogMapper;
import com.huangzizhu.pojo.OperationLog;
import com.huangzizhu.utils.CommonUtils;
import com.huangzizhu.utils.CurrentHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogMapper logMapper;

    @Around("@annotation(com.huangzizhu.annotion.Log)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取HttpServletRequest
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = CommonUtils.getClientIp(request);
        long startTime = System.currentTimeMillis(); // 开始时间
        Object result = joinPoint.proceed(); // 执行目标方法
        long endTime = System.currentTimeMillis(); // 结束时间
        long costTime = endTime - startTime; // 计算耗时
        Integer id = CurrentHolder.getCurrentId();
        Boolean isAdmin = CurrentHolder.getCurrentIsAdmin();
        Integer operateType;
        if(isAdmin != null) operateType = isAdmin  ? 2 : 1;
        else operateType = 0;
        OperationLog operateLog = CommonUtils.getOperateLog(joinPoint, result, costTime, ip, id, operateType);
        // 记录日志
        logMapper.insert(operateLog);
        return result;
    }

}
