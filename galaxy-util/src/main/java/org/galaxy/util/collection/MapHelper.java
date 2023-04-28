package org.galaxy.util.collection;

import org.checkerframework.checker.units.qual.K;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 常用Map的帮助类
 */
public final class MapHelper {

    private MapHelper(){}

    /**
     * 返回一个空的HashMap
     * @param <K>  Map的key类型
     * @param <V>  Map的value类型
     * @return     返回一个空的HashMap
     */
    public static <K, V> Map<K, V> create(){
        return new HashMap<>();
    }

    /**
     * 初始化一个Map，提供一对key/value
     * @param key    Map的key
     * @param value  Map的value
     * @param <K>    Map的key类型
     * @param <V>    Map的value类型
     * @return       返回一个初始化的map
     */
    public static <K, V> Map<K, V> of(K key, V value){
        if(Objects.isNull(key)){
            throw new IllegalArgumentException("key can not be null");
        }
        Map<K,V> newMap = new HashMap<>();
        newMap.put(key,value);
        return newMap;
    }

    /**
     * 基于一个Map复制出一个新的HashMap
     * @param source  源Map
     * @param <K>     源Map的key类型
     * @param <V>     源Map的value类型
     * @return        返回一个新的HashMap
     */
    public static <K, V> Map<K, V> copy(final Map<K, V> source) {
        return source == null ? null : new HashMap<>(source);
    }

    /**
     * 给一个map的所有key都加上前缀prefix
     * @param source    需要加prefix的源Map
     * @param prefix    前缀字符串
     * @param <K>       源Map的key类型
     * @param <V>       源Map的value类型
     * @return          返回一个新的Map，key值都加上前缀
     */
    public static <K, V> Map<String, V> prefix(final Map<K, V> source, final String prefix) {
        if (source == null) {
            return null;
        }
        final Map<String, V> result = new HashMap<>();
        for (final Map.Entry<K, V> entry : source.entrySet()) {
            final K key = entry.getKey();
            final V value = entry.getValue();
            result.put(prefix + '.' + key.toString(), value);
        }
        return result;
    }

    /**
     * 将两个Map合成一个新的map，两个Map的key,value的类型必须一致
     * @param lhs   左Map
     * @param rhs   右Map
     * @param <K>   Map的key类型
     * @param <V>   Map的value类型
     * @return      返回一个合并后的新Map
     */
    public static <K, V> Map<K, V> merge(final Map<K, V> lhs, final Map<K, V> rhs) {
        Map<K, V> result = null;
        if (lhs == null || lhs.isEmpty()) {
            result = copy(rhs);
        }
        else if (rhs == null || rhs.isEmpty()) {
            result = copy(lhs);
        }
        else {
            result = copy(lhs);
            result.putAll(rhs);
        }
        return result;
    }

    /**
     * 将一个Map的key,value进行倒转，返回一个新的Map,若value存在空值，则倒转的时候直接忽略
     * @param map 要被倒转的Map
     * @param <K> map的key类型
     * @param <V> map的value类型
     * @return    返回一个倒转后的map
     */
    public static <K, V> Map<V,K> inverseMap(Map<K, V> map) {
        Map<V,K> inverseMap = new HashMap<V,K>();

        if(Objects.isNull(map)){
            return inverseMap;
        }
        for(Map.Entry<K, V> entry: map.entrySet()) {
            if(Objects.nonNull(entry.getValue())){
                inverseMap.put(entry.getValue(), entry.getKey());
            }
        }
        return inverseMap;
    }

    /**
     * 判断一个map的value是否存在null值
     * @param map 待判断的map
     * @return 是否存在null值
     * @param <K> 泛型参数
     * @param <V> 泛型参数
     *
     * map 为空或者空map都认为是存在null值
     */
    public static <K, V> boolean existsNullValue(Map<K, V> map){
        if (Objects.isNull(map)) {
            return true;
        }
        if (map.isEmpty()) {
            return true;
        }

        for (Map.Entry<K, V> entry: map.entrySet()) {
            if(Objects.isNull(entry.getValue())){
                return true;
            }
        }
        return false;
    }
}
