package org.galaxy.lock.aop;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 防重复提交注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RepeatSubmit {

    /**
     * 分布式锁的key，支持Spel表达式
     */
    @AliasFor("value")
    String key() default "default";

    /**
     * 和key一样
     */
    @AliasFor("key")
    String value() default "default";

    /**
     * 分布式锁的key的前缀，添加在key的前面，用来区分
     * 这里的默认值是空字符串，AOP处理的时候会给他加上
     */
    String prefix() default "";

    /**
     * 提交时间间隔限制
     * 默认 30s 单位是秒
     * @return Time unit is one second
     */
    int limit() default 30;

    /**
     * 防重复的key的策略，默认使用SPEL表达式
     */
    RepeatSubmitStrategy repeatSubmitStrategy() default RepeatSubmitStrategy.EXPRESSION_PARAMETER;

    /**
     * 请求完成后 key是否删除
     * true则删除，false 则等待失效
     */
    boolean isDelete() default true;
}
