package org.galaxy.util.core;

import org.springframework.lang.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

public class ClassUtils {

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap<>(9);
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap<>(9);

    static {
        primitiveWrapperTypeMap.put(Boolean.class, Boolean.TYPE);
        primitiveWrapperTypeMap.put(Byte.class, Byte.TYPE);
        primitiveWrapperTypeMap.put(Character.class, Character.TYPE);
        primitiveWrapperTypeMap.put(Double.class, Double.TYPE);
        primitiveWrapperTypeMap.put(Float.class, Float.TYPE);
        primitiveWrapperTypeMap.put(Integer.class, Integer.TYPE);
        primitiveWrapperTypeMap.put(Long.class, Long.TYPE);
        primitiveWrapperTypeMap.put(Short.class, Short.TYPE);
        primitiveWrapperTypeMap.put(Void.class, Void.TYPE);

        for (Map.Entry<Class<?>, Class<?>> classClassEntry : primitiveWrapperTypeMap.entrySet()) {
            primitiveTypeToWrapperMap.put(classClassEntry.getValue(), classClassEntry.getKey());
        }
    }

    private ClassUtils() {}

    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        } else {
            Class<?> resolvedWrapper;
            if (lhsType.isPrimitive()) {
                resolvedWrapper = primitiveWrapperTypeMap.get(rhsType);
                return lhsType == resolvedWrapper;
            } else {
                resolvedWrapper = primitiveTypeToWrapperMap.get(rhsType);
                return resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper);
            }
        }
    }

    public static boolean isAssignableValue(Class<?> type, @Nullable Object value) {
        return value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive();
    }

}
