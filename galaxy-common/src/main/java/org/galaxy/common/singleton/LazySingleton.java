package org.galaxy.common.singleton;

import java.util.Objects;

/**
 * 同步延迟加载，又叫懒汉
 *
 * 执行效率低，无论是否已经存在实例，在多线程的情况下都会发生阻塞
 */
public class LazySingleton {

    private volatile static LazySingleton instance = null;

    private LazySingleton(){}

    public static synchronized LazySingleton getInstance(){
        if(Objects.isNull(instance)){
            instance = new LazySingleton();
        }
        return instance;
    }
}
