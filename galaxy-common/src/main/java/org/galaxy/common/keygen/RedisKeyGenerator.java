package org.galaxy.common.keygen;

import java.util.Properties;

/**
 * 利用redis的incr来生成ID
 * 单节点redis在高并发下生成ID可能会出现性能瓶颈，解决思路：redis集群，设置5个key
 * 分别叫ID_Step_1,ID_Step_2,ID_Step_3,ID_Step_4,ID_Step_5保证这5个key分别分布在集群中的5台master即可
 * 将压力分给5台，然后自增的时候每个key都自增5步骤
 */
public class RedisKeyGenerator implements KeyGenerator{

    @Override
    public Comparable<?> generateKey() {
        return null;
    }

    @Override
    public String getType() {
        return "Redis Incr";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
