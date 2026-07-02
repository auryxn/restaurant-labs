package com.auryxn.restaurantlabs.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("within(com.auryxn.restaurantlabs.service..*) || within(com.auryxn.restaurantlabs.controller.api..*)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        String method = joinPoint.getSignature().toShortString();
        log.debug("Entering {}", method);
        try {
            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();
            log.info("Completed {} in {} ms", method, stopWatch.getTotalTimeMillis());
            return result;
        } catch (Throwable ex) {
            if (stopWatch.isRunning()) stopWatch.stop();
            log.error("Exception in {} after {} ms: {}", method, stopWatch.getTotalTimeMillis(), ex.getMessage());
            throw ex;
        }
    }
}
