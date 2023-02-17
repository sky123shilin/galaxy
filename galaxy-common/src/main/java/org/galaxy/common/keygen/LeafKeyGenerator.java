package org.galaxy.common.keygen;

import java.util.Properties;

/**
 * 美团Leaf
 * 解决了时钟回拨问题
 * 解决了WorkID和Data Center ID的管理
 */
public class LeafKeyGenerator implements KeyGenerator {

    @Override
    public Comparable<?> generateKey() {
        return null;
    }

    @Override
    public String getType() {
        return "leaf";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
