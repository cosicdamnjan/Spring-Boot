package com.damnj.hello.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class GoogleTranslateControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTranslateControllerAspect.class  );

    @Autowired
    private ObjectMapper mapper;
    /**
     *
     */
    @Pointcut("within(com.damnj.hello.controller..*) " + "&&  @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcut(){

    }

    @Before( "pointcut()" )
    public void logMethod(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation( RequestMapping.class);

        Map<String, Object> parameters = getParameters( joinPoint);

        try {
            logger.info("Request:==> path(s): {}, method(s): {}, arguments: {} ",
                    mapping.path(), mapping.method(), mapper.writeValueAsString(parameters));
        } catch (JsonProcessingException e) {
            logger.error("Error while converting", e);
        }

    }

    @AfterReturning(pointcut = "pointcut()", returning = "output")
    public void logMethodAfter(JoinPoint joinPoint, String output) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);

        try {
            logger.info("Response : <== path(s): {}, method(s): {}, retuning: {}",
                    mapping.path(), mapping.method(), output);
        } catch (Exception e) {
            logger.error("Error while converting", e);
        }
    }

    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        HashMap<String, Object> map = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        return map;
    }
}
