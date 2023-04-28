package org.galaxy.common.singleton;

import java.util.Objects;

/**
 * 使用ThreadLocal修复双重检测
 */
public class ThreadLocalSingleton {
    private static final ThreadLocal<ThreadLocalSingleton> perThreadInstance = new ThreadLocal<>();
    private static ThreadLocalSingleton singleton ;
    private ThreadLocalSingleton() {}

    public static ThreadLocalSingleton getInstance() {
        if (Objects.isNull(perThreadInstance.get() )){
            // 每个线程第一次都会调用
            createInstance();
        }
        return singleton;
    }

    private static void createInstance() {
        synchronized (ThreadLocalSingleton.class) {
            if (singleton == null){
                singleton = new ThreadLocalSingleton();
            }
        }
        perThreadInstance.set(singleton);
    }
}
