package org.galaxy.util.exception;

/**
 * 异常工具类
 */
public final class ExceptionUtils {

    private ExceptionUtils(){}

    public static boolean isCheckedException(Throwable ex) {
        return !(ex instanceof RuntimeException) && !(ex instanceof Error);
    }
}
