package com.powernode.aop;

import com.alibaba.fastjson.JSON;
import com.powernode.ann.MyLog;
import com.powernode.domain.SysLog;
import com.powernode.service.SysLogService;
import com.powernode.util.AuthUtil;
import com.powernode.util.ThreadPoolUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;

@Aspect
@Component
public class MyLogAdvice {
    @Autowired
    private SysLogService sysLogService;

    //环绕通知
    @Around(value = "@annotation(com.powernode.ann.MyLog)")    //环绕通知，有著姐MyLog的方法才生效
    public Object doArount(ProceedingJoinPoint joinPoint) {
        Object result = null;

        //反射方法相关的信息
        //1.获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //2.获取方法信息
        Method method = methodSignature.getMethod();
        //3.获取方法名
        String methodName = method.getName();
        //4.获取类的信息
        Class<?> myclass = method.getDeclaringClass();
        String typeName = myclass.getTypeName();
        //5.完整的方法名
        String fullMethodName = typeName + "." + methodName;
        //6.获取参数
        Object[] args = joinPoint.getArgs();
        //7.获取用户id
        Long userId = AuthUtil.getSysUserId();
        //8.获取注解中的operation
        String operation = method.getAnnotation(MyLog.class).operation();
        //9.获取ip地址
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ip = requestAttributes.getRequest().getRemoteAddr();
        //10.开始时间
        long start = System.currentTimeMillis();
        //调用目标方法
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        //11.结束时间
        long end = System.currentTimeMillis();
        long time = end - start;
        //插入日志表
        SysLog sysLog = SysLog.builder()
                .createDate(new Date())
                .ip(ip)
                .method(fullMethodName)
                .operation(operation)
                .time(time)
                .userId(userId)
                .params(ObjectUtils.isEmpty(args)?"": JSON.toJSONString(args))
                .build();
        //插入日志表的方法万一数据库执行慢，导致主方法无法即使返回，提高性能，日志插入使用异步方式---线程

        //优化--使用线程池完成日志插入，不影响主方法调用
        ThreadPoolUtil.poolExecutor.submit(()->{
            sysLogService.save(sysLog);
        });

        return result;
    }
}
