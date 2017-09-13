package com.sccswd.sharing.aspect;

import com.sccswd.sharing.annotation.TableSharing;
import com.sccswd.sharing.plugin.TableSharingPlugin;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/12
 */
@Aspect
@Component
public class TableSharingAspect implements ApplicationContextAware {

    private static final ParameterNameDiscoverer LOCAL_VARIABLE_TABLE_PARAMETER_NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.sccswd.sharing.annotation.TableSharing)")
    private void sharingAspect() {

    }

    @Around("sharingAspect()")
    public Object doRound(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        TableSharing annotation = method.getAnnotation(TableSharing.class);
        Long value = getValue(proceedingJoinPoint, method, annotation);
        TableSharingPlugin.set(value);
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            TableSharingPlugin.remove();
        }
    }

    private Long getValue(ProceedingJoinPoint proceedingJoinPoint, Method method, TableSharing annotation) {
        Object[] args = proceedingJoinPoint.getArgs();
        String[] parameterNames = LOCAL_VARIABLE_TABLE_PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        for (int i = 0, len = parameterNames.length; i < len; i++) {
            standardEvaluationContext.setVariable(parameterNames[i], args[i]);
        }
        standardEvaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));
        return (Long) EXPRESSION_PARSER.parseExpression(annotation.key()).getValue(standardEvaluationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}