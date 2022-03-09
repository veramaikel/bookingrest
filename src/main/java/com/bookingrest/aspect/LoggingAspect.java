package com.bookingrest.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class LoggingAspect {

    Logger logger = Logger.getLogger(this.getClass());

    @AfterReturning(pointcut = "execution(* com.bookingrest.repository.*.findAll*(..))", returning = "page")
    public void serviceFindAllMethods(JoinPoint jp, Object page) {
        logger.info(getStringSignature(jp,true)+" returning "+((Page<?>)page).getTotalElements()+" records");
    }

    @AfterReturning(pointcut = "execution(* com.bookingrest.repository.*.findBy*(..))", returning = "entity")
    public void serviceFindByMethods(JoinPoint jp, Object entity) {
        logger.info(getStringSignature(jp,true)+" returning "+entity);
    }

    @AfterReturning(pointcut = "execution(* com.bookingrest.service.*.save*(..))", returning = "entity")
    public void serviceInsertMethods(JoinPoint jp, Object entity) {
        logger.info(getStringSignature(jp,false)+" inserted this record: "+entity);
    }

    @AfterReturning(pointcut = "execution(* com.bookingrest.service.*.update*(..))", returning = "entity")
    public void serviceUpdateMethods(JoinPoint jp, Object entity) {
        logger.info(getStringSignature(jp,false)+" updated this record: "+entity);
    }

    @AfterReturning(pointcut = "execution(* com.bookingrest.service.*.delete*(..))", returning = "bool")
    public void serviceDeleteMethods(JoinPoint jp, Object bool) {
        if((boolean)bool)
            logger.info(getStringSignature(jp,true)+" was successful");
        else
            logger.warn("An error occurred in the execution of "+getStringSignature(jp,true));
    }

    @AfterThrowing(pointcut = "within(com.bookingrest..*)", throwing = "ex")
    public void bookingRestThrowing(JoinPoint jp, Exception ex) {
        logger.error(getStringSignature(jp,true)+" resulted into exception, message: "+ ex.getMessage());
    }

    private String getStringSignature(JoinPoint jp, boolean full){
        CodeSignature codeSignature = (CodeSignature) jp.getSignature();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = jp.getArgs();
        StringBuilder builder = new StringBuilder(jp.getSignature().getDeclaringType().getCanonicalName()+".");
        builder.append(methodName).append('(');
        for (int i = 0; i < parameterValues.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(parameterNames[i]);
            if(full) {
                builder.append('=');
                builder.append(parameterValues[i]);
            }
        }
        builder.append(')');

        return builder.toString();
    }
}
