package com.bookstore.bookservice.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.bookstore.bookservice.*.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before executing {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(value = "execution(* com.bookstore.bookservice.*.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("After executing {} with result {}", joinPoint.getSignature().toShortString(), result);
    }
}
