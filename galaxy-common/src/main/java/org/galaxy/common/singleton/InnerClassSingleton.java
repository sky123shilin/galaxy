package org.galaxy.common.singleton;

/**
 * 采用内部类的方式实现单例
 * 目前这些方案都可以通过反射破坏其单例模式的特性，即通过修改构造函数的private来构造出多个来
 * 这个时候可以在私有构造方法上如下设计:
 * private InnerClassSingleton(){
 *         if(Holder.INSTANCE!=null){
 *             throw new RuntimeException("不允许非法访问");
 *         }
 *  }
 *  这样在通过反射创建实例时就会报错。
 */
public class InnerClassSingleton {

    private InnerClassSingleton() {}
    public static class Holder {
        static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }
    public static InnerClassSingleton getInstance() {
        // 外围类能直接访问内部类（不管是否是静态的）的私有变量
        return Holder.INSTANCE;
    }
}
