package org.galaxy.lock.aop;

public enum RepeatSubmitStrategy {

    /**
     * 策略一，所有参数都参与生成key并md5加密，key为prefix + md5加密后的字符串
     */
    ALL_PARAMETER,
    /**
     * 策略二，key支持Spel表达式，程序员自己选择方法中的参数作为key值，不过也要加个prefix
     */
    EXPRESSION_PARAMETER;

}
