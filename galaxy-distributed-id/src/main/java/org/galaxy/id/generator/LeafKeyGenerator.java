package org.galaxy.id.generator;

import org.springframework.stereotype.Component;

/**
 * 美团Leaf
 * 解决了时钟回拨问题
 * 解决了WorkID和Data Center ID的管理
 */
@Component
public class LeafKeyGenerator implements KeyGenerator {

    @Override
    public Comparable<?> generateId() {
        return null;
    }

    @Override
    public String supportType() {
        return Type.Leaf.name();
    }
}
