package org.galaxy.common.keygen;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成高并发下自增ID，但不满足分布式的场景
 * @author Shilin.Qu
 * @version V1.0
 */
public class IncrementKeyGenerator implements KeyGenerator{
    @Getter
    private final String type = "Increment";

    private final AtomicInteger count = new AtomicInteger();

    @Getter
    @Setter
    private Properties properties = new Properties();

    @Override
    public Comparable<?> generateKey() {
        return count.incrementAndGet();
    }
}
