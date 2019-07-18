package com.yfc.common.base;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author yfc
 * @date 2019-06-12
 * @description 日志切面
 * @parmas
 */
//声明这是一个切面
@Aspect
@Component
@Slf4j
public class LogAspect {
    private JSONObject jsonObject=new JSONObject();

    //声明一个切点
    @Pointcut("execution(public * com.yfc.serviceImpl..*(..))")
    private void controllerAspect(){

    }

    // 请求method前打印内容
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("===============请求内容开始===============");
        try {
            // 打印请求内容
            log.info("请求地址:" + request.getRequestURL().toString());
            log.info("请求方式:" + request.getMethod());
            log.info("请求类方法:" + joinPoint.getSignature());
            log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        } catch (Exception e) {
            log.error("###LogAspectServiceApi.class methodBefore() ### ERROR:", e);
        }
        log.info("===============请求内容结束===============");
    }

    // 在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void methodAfterReturing(Object o) {
        log.info("--------------返回内容开始----------------");
        try {
            log.info("Response内容:" + jsonObject.toJSONString(o));
        } catch (Exception e) {
            log.error("###LogAspectServiceApi.class methodAfterReturing() ### ERROR:", e);
        }
        log.info("--------------返回内容结束----------------");
    }
}
