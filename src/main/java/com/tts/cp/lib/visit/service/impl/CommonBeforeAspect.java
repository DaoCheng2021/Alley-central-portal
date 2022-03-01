package com.tts.cp.lib.visit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alley zhao created on 2022/2/11.
 */
@Slf4j
@Aspect
@Configuration
public class CommonBeforeAspect {

    @Before("execution(* com.tts.cp.lib.visit.service.impl.CourseServiceImpl.test(String))")
    public void beforeOpenTemplate(JoinPoint joinPoint) {
        //todo: save user action
        log.info("open template");
        List<Object> list = Arrays.asList(joinPoint.getArgs());
        System.out.println("这是公共的接口->test:");
    }
}
