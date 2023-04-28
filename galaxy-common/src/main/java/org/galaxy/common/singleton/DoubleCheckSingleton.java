package org.galaxy.common.singleton;

import java.util.Objects;

/**
 * 双重检测同步延迟加载
 */
public final class DoubleCheckSingleton {
    private volatile static DoubleCheckSingleton instance = null;

    private DoubleCheckSingleton(){}

    public static DoubleCheckSingleton getInstance(){
        //确定是否需要阻塞
        if(Objects.isNull(instance)){
            synchronized (DoubleCheckSingleton.class){
                //确定是否需要创建实例
                if(Objects.isNull(instance)){
                    //这里在多线程的情况下会出现指令重排的问题，所以对共有资源instance使用关键字volatile修饰
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
