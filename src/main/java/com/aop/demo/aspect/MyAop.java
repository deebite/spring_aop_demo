package com.aop.demo.aspect;

import com.aop.demo.annotation.AutoValidation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.regex.Pattern;


@Aspect
@Component
public class MyAop {

    private static final Logger LOG = LoggerFactory.getLogger(MyAop.class);

    @Autowired
    private Environment environment;

    @Autowired
    private MessageSource messageSource;

    @Pointcut("execution(* com.aop.demo.controller.AOPController.getUserDetails(..))")
    public void userDetailsPointCut() {
    }

    @Before("userDetailsPointCut()")
    public void beforeCall(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        for (Object arg: args) {
            Class<?> clazz = arg.getClass();
            if(clazz.isAnnotationPresent(AutoValidation.class)){
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    String fieldValue = (String) field.get(arg);
                    String regex = environment.getProperty(clazz.getSimpleName().toLowerCase()+"."+fieldName+".regex");
                    String isOptional = environment.getProperty(clazz.getSimpleName().toLowerCase()+"."+fieldName+".isOptional");
                    if((isOptional != null
                            && isOptional.equals("yes")
                            && !fieldValue.isEmpty()
                            && regex != null
                            && !Pattern.matches(regex, fieldValue)) ||
                            (fieldValue.equals("null")) ||
                            (regex != null && !Pattern.matches(regex, fieldValue))){
                        String message = messageSource.getMessage(clazz.getSimpleName().toLowerCase() + "." + fieldName + ".error.regex.message", null, Locale.ENGLISH);
                        throw new IllegalArgumentException(message + " : " + fieldName);
                    }
                }
            }

        }
        LOG.info("Before the function");
        System.out.println("Before the function");
    }
}
