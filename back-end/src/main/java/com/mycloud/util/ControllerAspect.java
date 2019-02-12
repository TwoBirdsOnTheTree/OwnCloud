package com.mycloud.util;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerAspect {

    /**
     * com.mycloud.controller.TestController
     */
    @Pointcut("execution(* com.mycloud.controller.*.*(..))")
    public void anyCall() {
    }

    @Around(value = "anyCall()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (null == requestAttributes) {
                return joinPoint.proceed();
            }
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 执行
            result = joinPoint.proceed();
            // 日志
            log(joinPoint, request, result);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    @Async
    public void log(ProceedingJoinPoint joinPoint, HttpServletRequest request, Object result) {
        System.out.println("请求参数: " + JSON.toJSONString(request.getParameterMap()));
    }
}
