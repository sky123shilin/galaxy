package org.galaxy.id.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * 利用redis的incr来生成ID
 * 单节点redis在高并发下生成ID可能会出现性能瓶颈，解决思路：redis集群，设置5个key
 * 分别叫ID_Step_1,ID_Step_2,ID_Step_3,ID_Step_4,ID_Step_5保证这5个key分别分布在集群中的5台master即可
 * 将压力分给5台，然后自增的时候每个key都自增5步骤
 *
 * 方案：
 * 使用redis的incr方法进行自增补零。然后结合时间、随机数、前缀组成唯一的流水号
 * 下面是流水号的结构：
 * OR 20230404 0000001   2364
 * 前缀 时间      自增     随机数
 */
@Component
@Slf4j
public class RedisIncrementKeyGenerator implements KeyGenerator {

    @Override
    public Comparable<?> generateId() {
        return null;
    }

    @Override
    public String supportType() {
        return Type.Redis_Increment.name();
    }
}
