package org.galaxy.common.keygen;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;
import java.util.UUID;

/**
 * UUID生成分布式ID
 * 性能好，长度过长,且无序
 */
public class UUIDKeyGenerator implements KeyGenerator{

    @Getter
    @Setter
    private Properties properties = new Properties();

    @Override
    public Comparable<?> generateKey() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getType() {
        return "UUID";
    }
}
