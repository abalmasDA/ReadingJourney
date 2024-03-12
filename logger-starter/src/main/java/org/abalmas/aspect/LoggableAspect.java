package org.abalmas.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggableAspect {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  @AfterReturning(pointcut = "@annotation(Loggable)", returning = "result")
  public void logMethodResult(JoinPoint joinPoint, Object result) {
    logger.info(joinPoint.getSignature() + " returned: " + result);
  }

  @AfterThrowing(pointcut = "@annotation(Loggable)", throwing = "exception")
  public void logMethodException(JoinPoint joinPoint, Throwable exception) {
    logger.error(joinPoint.getSignature() + " threw exception: " + exception.getMessage(),
        exception);
  }

}
