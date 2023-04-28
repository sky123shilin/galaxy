package org.galaxy.util.collection;

import java.util.HashSet;
import java.util.Set;

/**
 * Set工具类
 */
public final class SetHelper {

    private SetHelper(){}

    public static <E> Set<E> create(){
        return new HashSet<>();
    }

}
