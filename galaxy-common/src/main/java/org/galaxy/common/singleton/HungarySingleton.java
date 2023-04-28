package org.galaxy.common.singleton;

/**
 * 非延迟加载，又叫饿汉
 */
public class HungarySingleton {

    private HungarySingleton(){}

    private static final HungarySingleton instance = new HungarySingleton();

    public static HungarySingleton getInstance(){
        return instance;
    }

}
