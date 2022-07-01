package net.sjl.spring.logger.aspect;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

  @Around("@annotation(net.sjl.spring.logger.annotation.LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;
    System.out.println("-----> " + joinPoint.getSignature() + " was executed within " + duration + "ms");
    return proceed;
  }
  
  
  @PostConstruct
  public void postCreation() {
	  System.out.println("-----> LoggerAspect bean is created");
  }
}
