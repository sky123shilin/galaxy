package org.galaxy.util.guava;

import com.google.common.collect.HashMultimap;

/**
 * 常用于分组功能，比如Map<K,Collection<V> V>
 */
public class MultiMapStudy {

    public static void main(String[] args) {
        HashMultimap<Integer,Integer> map = HashMultimap.create();

        map.put(1,1);
        map.put(1,2);
        map.put(1,3);
        map.put(2,2);

        System.out.println(map.toString());
    }
}
