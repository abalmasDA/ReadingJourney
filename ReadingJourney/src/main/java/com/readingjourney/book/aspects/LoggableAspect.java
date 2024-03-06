package com.readingjourney.book.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggableAspect {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @AfterReturning(pointcut = "@annotation(Loggable)", returning = "result")
  public void logMethodResult(JoinPoint joinPoint, Object result) {
    logger.info("Method " + joinPoint.getSignature() + " returned with value: " + result);
  }

}