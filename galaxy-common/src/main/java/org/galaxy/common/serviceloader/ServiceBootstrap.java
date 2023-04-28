package org.galaxy.common.serviceloader;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * 利用Java自带的SPI机制加载接口实现类
 */
public final class ServiceBootstrap {

    private ServiceBootstrap(){}

    /**
     * 加载/META-INF/services/file中第一个实现类
     * @param clazz 接口class
     * @return 返回实现类对象
     * @param <S> 泛型参数
     */
    public static <S> S loadFirst(Class<S> clazz) {
        Iterator<S> iterator = loadAll(clazz);
        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format("No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!", clazz.getName()));
        } else {
            return iterator.next();
        }
    }

    /**
     * 加载/META-INF/services/file中所有实现类，返回遍历器Iterator
     * @param clazz 接口class
     * @return 返回实现类对象
     * @param <S> 泛型参数
     */
    public static <S> Iterator<S> loadAll(Class<S> clazz) {
        ServiceLoader<S> loader = ServiceLoader.load(clazz);
        return loader.iterator();
    }

    /**
     * 加载/META-INF/services/file中优先级高的第一个类，必须实现Ordered接口
     * @param clazz 接口class
     * @return 返回实现类对象
     * @param <S> 泛型参数
     */
    public static <S extends Ordered> S loadPrimary(Class<S> clazz) {
        List<S> candidates = loadAllOrdered(clazz);
        return candidates.get(0);
    }

    /**
     * 加载/META-INF/services/file中所有实现类（且按照order进行排序），必须实现Ordered接口
     * @param clazz 接口class
     * @return 返回实现类对象
     * @param <S> 泛型参数
     */
    public static <S extends Ordered> List<S> loadAllOrdered(Class<S> clazz) {
        Iterator<S> iterator = loadAll(clazz);
        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format("No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!", clazz.getName()));
        } else {
            List<S> candidates = Lists.newArrayList(iterator);
            candidates.sort(Comparator.comparingInt(Ordered::getOrder));
            return candidates;
        }
    }

}
