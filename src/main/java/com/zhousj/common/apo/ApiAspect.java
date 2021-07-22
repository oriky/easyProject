package com.zhousj.common.apo;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 针对上云认证控制器，打印入参和出参，请求参数非空校验
 *
 * @author zhousj
 * @date 2021/7/19
 */
@Aspect
@Component
public class ApiAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void dealPoint() {

    }

    @Before("dealPoint()")
    public void executeBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("线程:{}接收请求，执行方法:{}，请求参数:{}", Thread.currentThread().getName(),
                methodName, JSONObject.toJSONString(joinPoint.getArgs(), true));

    }

    @AfterReturning(returning = "obj", pointcut = "dealPoint()")
    public void afterReturn(Object obj) {
        logger.info("线程:{}请求结束,返回参数{}", Thread.currentThread().getName(), JSONObject.toJSONString(obj, true));
    }

}
