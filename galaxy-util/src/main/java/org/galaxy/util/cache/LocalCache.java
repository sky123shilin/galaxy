package org.galaxy.util.cache;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 非常简易的本地缓存实现
 * 缓存淘汰采用懒加载
 * @author Shilin.Qu
 *
 */
public class LocalCache {

    // 类型建议用抽象父类，兼容性更好
    private static final ConcurrentMap<String, CacheData> cacheRepository = new ConcurrentHashMap<>();

    // 缓存最大key的数量，超出则自动淘汰
    private static final int MAX_CACHE_SIZE = 1000;

    private static int cacheSize;

    // 缓存默认大小是1000
    static{
        cacheSize = MAX_CACHE_SIZE;
    }

    /**
     * 设置缓存大小
     * @param size  缓存大小
     */
    public static void setCacheSize(int size){
        cacheSize = size;
    }

    /**
     * 设置缓存
     * @param key     缓存对应的key
     * @param val     缓存对应的value
     * @param expiredTime   缓存过期时间
     * @return        返回是否设置成功
     */
    public static boolean set(String key, Object val, long expiredTime){
        if (isNotValid(key)) {
            return false;
        }
        if (val == null) {
            remove(key);
        }
        if (expiredTime <= 0) {
            remove(key);
        }
        // 每次设置缓存之前先清空目前过期缓存
        cleanTimeoutCache();
        long timeout = System.currentTimeMillis() + expiredTime;
        CacheData cacheData = new CacheData(key, val, timeout);
        cacheRepository.put(cacheData.getKey(), cacheData);
        return true;
    }

    /**
     * 删除key对应的缓存
     * @param key    缓存的key
     */
    public static void remove(String key){
        if (isNotValid(key)) {
            return;
        }
        cacheRepository.remove(key);
    }

    /**
     * 获取缓存中对应的key的值
     * @param key   缓存的key
     * @return      缓存的value
     */
    public static Object get(String key){
        if (isNotValid(key)) {
            return null;
        }
        cleanTimeoutCache();
        CacheData localCacheData = cacheRepository.get(key);
        if (localCacheData!=null && System.currentTimeMillis()<localCacheData.getTimeoutTime()) {
            return localCacheData.getVal();
        } else {
            remove(key);
            return null;
        }
    }

    /**
     * 清除过期缓存
     */
    public static void cleanTimeoutCache(){
        if (!cacheRepository.keySet().isEmpty()) {
            for (String key: cacheRepository.keySet()) {
                CacheData localCacheData = cacheRepository.get(key);
                if (localCacheData!=null && System.currentTimeMillis() >= localCacheData.getTimeoutTime()) {
                    cacheRepository.remove(key);
                }
            }
        }
    }

    private static boolean isNotValid(String key){
        return Objects.isNull(key) || key.trim().length() == 0;
    }

    /**
     * 本地缓存内部类
     */
    private static class CacheData{
        private String key;
        private Object val;
        private long timeout;

        public CacheData(String key, Object val, long timeout) {
            this.key = key;
            this.val = val;
            this.timeout = timeout;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getVal() {
            return val;
        }

        public void setVal(Object val) {
            this.val = val;
        }

        public long getTimeoutTime() {
            return timeout;
        }

        public void setTimeoutTime(long timeout) {
            this.timeout = timeout;
        }
    }

}
