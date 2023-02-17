package org.galaxy.common.keygen;

import java.util.Properties;

/**
 * 一次按需批量生成多个ID，每次生成都需要访问数据库，将数据库修改为最大的ID值，并在内存中记录当前值及最大值。
 * 数据库如果是单点的，存在单点故障，且服务重启的话会造成ID不连续问题
 */
public class BatchDBKeyGenerator implements KeyGenerator{

    @Override
    public Comparable<?> generateKey() {
        return null;
    }

    @Override
    public String getType() {
        return "batch_db";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
