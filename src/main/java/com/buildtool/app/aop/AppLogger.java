package com.buildtool.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppLogger {

	@Pointcut("within(com.buildtool.app..*)")
	protected void loggingOperation() {

	}

	@Around("loggingOperation()")
	public Object enteringLogging(ProceedingJoinPoint joinPoint) {
		System.out.println("inside the aspect=====================");
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		Object result = null;
		logger.info("entering " + joinPoint.getSignature().getName());
		try {
			result = joinPoint.proceed();
			logger.info("exiting " + joinPoint.getSignature().getName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

}
