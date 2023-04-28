package org.galaxy.lock.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.galaxy.lock.crypto.MD5;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class RepeatSubmitAspect {
    @Resource
    private RedissonClient redissonClient;

    private final ExpressionParser parser = new SpelExpressionParser();

    private final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(org.galaxy.lock.aop.RepeatSubmit)")
    private void pointcut() {}

    /**
     * 核心逻辑，处理注解@FormRepeatSubmit，完成防重复逻辑
     * 利用Redisson来实现分布式锁
     * @param joinPoint 主体
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取注解信息
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);

        // 解析注解数据
        boolean isDelete = annotation.isDelete();//是否删除
        int limit = annotation.limit();//防重复时间
        RepeatSubmitStrategy strategy = annotation.repeatSubmitStrategy();//key生成策略
        String prefix = annotation.prefix();//key前缀

        // 生成锁的key
        String key = (strategy == RepeatSubmitStrategy.ALL_PARAMETER) ?
                buildKeyByAllParameter(prefix,method,joinPoint.getArgs()) : buildKeyByExpression(prefix,annotation.key(),method,joinPoint.getArgs());

        // 利用分布式锁来实现并发控制
        RLock lock = redissonClient.getLock(key);
        if(!lock.tryLock(0,limit,TimeUnit.SECONDS)){
            // 此处或抛异常或返回值，自定义处理
            System.out.println("fail to get lock");
            return "fail to get lock";
        }

        // 执行业务逻辑
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Exception in {}.{}() with cause = {} and exception = {}",
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                    e.getCause() != null? e.getCause() : "NULL", e.getMessage(), e);
            throw e;
        }finally {
            if(isDelete) {
                lock.unlock();
            }
        }
    }

    /**
     * 构造分布式锁的唯一键，利用注解所在的方法名+方法参数值然后md5加密来构造
     * @param method  方法
     * @param args   方法参数列表
     * @return 加密后的唯一值
     */
    private String buildKeyByAllParameter(String prefix, Method method, Object... args){
        StringBuilder sb = new StringBuilder(method.getName());
        for (Object arg : args) {
            sb.append(toString(arg));
        }
        return MD5.encodeAsHex(sb.toString());
    }

    /**
     * 解析Spel表达式，利用方法参数真正的值来替换它
     * @param expression 表达式
     * @param method 注解所在的方法
     * @param args  参数值列表
     * @return 解析后的字符串值
     */
    private String buildKeyByExpression(String prefix, String expression, Method method, Object... args){
        EvaluationContext context = new StandardEvaluationContext();
        String[] params = discoverer.getParameterNames(method);
        for (int i = 0; i < Objects.requireNonNull(params).length; i++) {
            context.setVariable(params[i],args[i]);
        }
        return prefix.concat(Objects.requireNonNull(parser.parseExpression(expression).getValue(context, String.class)));
    }

    private String toString(Object arg) {
        if (Objects.isNull(arg)) {
            return "null";
        }
        if (arg instanceof Number) {
            return arg.toString();
        }
        return "";
    }

}
