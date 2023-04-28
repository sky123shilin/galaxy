package org.galaxy.id.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * UUID生成分布式ID
 * 性能好，长度过长,且无序
 * 且支持分布式Id场景
 * 32位随机字符串
 */
@Component
@Slf4j
public class UUIDKeyGenerator implements KeyGenerator{
    @Override
    public Comparable<?> generateId() {
        String key = UUID.randomUUID().toString().replace("-","");
        log.info("生成UUID类型的分布式ID：{}", key);
        return key;
    }

    @Override
    public String supportType() {
        return Type.UUID.name();
    }
}
