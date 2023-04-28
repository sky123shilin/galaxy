package org.galaxy.util.core;

import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class ClassLoaderUtils {

    private ClassLoaderUtils(){}

    private static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP = new ConcurrentHashMap<>(32);

    static {
        List<Class<?>> primitiveTypes = new ArrayList<>(32);
        primitiveTypes.add(Boolean.TYPE);
        primitiveTypes.add(Byte.TYPE);
        primitiveTypes.add(Character.TYPE);
        primitiveTypes.add(Double.TYPE);
        primitiveTypes.add(Float.TYPE);
        primitiveTypes.add(Integer.TYPE);
        primitiveTypes.add(Long.TYPE);
        primitiveTypes.add(Short.TYPE);
        primitiveTypes.add(boolean[].class);
        primitiveTypes.add(byte[].class);
        primitiveTypes.add(char[].class);
        primitiveTypes.add(double[].class);
        primitiveTypes.add(float[].class);
        primitiveTypes.add(int[].class);
        primitiveTypes.add(long[].class);
        primitiveTypes.add(short[].class);
        primitiveTypes.add(Void.TYPE);
        for (Class<?> primitiveType : primitiveTypes) {
            PRIMITIVE_TYPE_NAME_MAP.put(primitiveType.getName(), primitiveType);
        }
    }

    /**
     * 获取当前线程中的ClassLoader对象
     * @return  ClassLoader对象
     * getContextClassLoader()
     * 返回该线程的ClassLoader上下文。线程创建者提供ClassLoader上下文，以便运行在该线程的代码在加载类和资源时使用。如果没有，则默认返回父线程的ClassLoader上下文。
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取当前的ClassLoader
     * @return ClassLoader对象
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (Objects.isNull(classLoader)) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
            if (Objects.isNull(classLoader)) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 加载一个类到内存
     * @param name 类的全限定名称
     * @param classLoader 要加载类的ClassLoader
     * @param isInitialized 是否执行类的初始化
     * @return 类的Class对象
     */
    public static Class<?> loadClass(String name, ClassLoader classLoader, boolean isInitialized) {
        Class<?> clazz = loadPrimitiveClass(name);
        if (Objects.nonNull(clazz)) {
            return clazz;
        }
        String elementName;
        Class<?> elementClass;
        if (name.endsWith("[]")) {
            elementName = name.substring(0, name.length() - "[]".length());
            elementClass = loadClass(elementName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else if (name.startsWith("[L") && name.endsWith(";")) {
            elementName = name.substring("[L".length(), name.length() - 1);
            elementClass = loadClass(elementName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else if (name.startsWith("[")) {
            elementName = name.substring("[".length());
            elementClass = loadClass(elementName, classLoader, isInitialized);
            clazz = Array.newInstance(elementClass, 0).getClass();
        } else {
            if (Objects.isNull(classLoader)) {
                classLoader = getClassLoader();
            }
            try {
                clazz = Class.forName(name, isInitialized, classLoader);
            } catch (ClassNotFoundException var6) {
                clazz = tryLoadInnerClass(name, classLoader, isInitialized);
                if (Objects.isNull(clazz)) {
                    throw new IllegalArgumentException(var6.getMessage());
                }
            }
        }
        return clazz;
    }

    /**
     * 加载一个类到内存
     * @param name 类的全限定名称
     * @param isInitialized 是否执行类的初始化
     * @return 类的Class对象
     */
    public static Class<?> loadClass(String name, boolean isInitialized) {
        return loadClass(name, null, isInitialized);
    }

    /**
     * 加载一个类到内存
     * @param name 类的全限定名称
     * @return 类的Class对象
     */
    public static Class<?> loadClass(String name) {
        return loadClass(name, true);
    }

    public static Class<?> loadPrimitiveClass(String name) {
        Class<?> result = null;
        if (StringUtils.hasText(name)) {
            name = name.trim();
            if (name.length() <= 8) {
                result = PRIMITIVE_TYPE_NAME_MAP.get(name);
            }
        }
        return result;
    }

    /**
     * 类是否在类路径上存在
     * @param className  类的全限定名称
     * @return 布尔值，是否存在
     */
    public static boolean isPresent(String className) {
        return isPresent(className, null);
    }

    /**
     * 类是否在类路径上存在
     * @param className 类的全限定名称
     * @param classLoader 加载类的loader
     * @return 布尔值，是否存在
     */
    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            loadClass(className, classLoader, false);
            return true;
        } catch (Throwable var3) {
            return false;
        }
    }

    private static Class<?> tryLoadInnerClass(String name, ClassLoader classLoader, boolean isInitialized) {
        int lastDotIndex = name.lastIndexOf(46);
        if (lastDotIndex > 0) {
            String innerClassName = name.substring(0, lastDotIndex) + '$' + name.substring(lastDotIndex + 1);
            try {
                return Class.forName(innerClassName, isInitialized, classLoader);
            } catch (ClassNotFoundException var6) {
                System.out.println("do nothing");
            }
        }
        return null;
    }

}
