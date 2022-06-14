package com.dislinkt.connectionservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {}

    @Pointcut("within(com.dislinkt.connectionservice.controller..*)" +
            " || within(com.dislinkt.connectionservice.dao..*)" +
            " || within(com.dislinkt.connectionservice.service..*)")
    public void applicationPackagePointcut(){}

    @Pointcut("!@annotation(com.dislinkt.connectionservice.logging.NoLogging)")
    public void noLoggingAnnotationPointcut(){}

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut() && noLoggingAnnotationPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e != null ? e.getMessage() : "NULL");
    }

    @AfterReturning(pointcut = "applicationPackagePointcut() && springBeanPointcut() && noLoggingAnnotationPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.debug("Return value of {}.{}() = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
    }

    @Around("applicationPackagePointcut() && springBeanPointcut() && noLoggingAnnotationPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
