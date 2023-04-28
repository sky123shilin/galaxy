package org.galaxy.id.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 利用Mongo的ObjectId算法来生成Id
 * objectId 是 mongoDb 开源的分布式 id 生成算法。其核心思想就是：使用12个字节生成全局唯一id （24个16进制字符）。
 * 生成规则：
 * 4个字节：秒级别时间戳
 * 5个字节：随机数（mongo-java-driver3.4之前的版本为3个字节主机名+2个字节进程号，但在docker容器中，存在bug）
 * 3个字节：自增计数器（初始化一个随机数，然后递增）
 * 解析：
 * 1、先存时间戳，是为了保证分布式系统内生成的 id 趋势递增。
 * 2、5个字节的随机数，使不同机器生成机器码碰撞概率非常低，为 1/(2的40次方)。进程启动时确定随机值，后续生成 id 时随机数不变。
 * 3、自增计数器，自增时需要保证线程安全，确保一秒钟内能生成 2的24次方（约1600w）个不重复的id。（实际业务，单机器不会有这么高的并发量的。）
 *
 * 适用于分布式环境下的分布式ID
 */
@Component
@Slf4j
public class ObjectIdKeyGenerator implements KeyGenerator {

    private static final char[] HEX_UNIT = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 系统起始时间(1646064000 : 2022-03-01 00:00:00 )，算法有效期： 138年。
     */
    private final static long SYSTEM_START_TIME = 1646064000L;
    /**
     * 计数器，自增id
     */
    private static final AtomicInteger SEQUENCE_COUNTER = new AtomicInteger(ThreadLocalRandom.current().nextInt());
    /**
     * 初始化机器码
     */
    private static final char[] MACHINE_CODE = initMachineCode();

    @Override
    public Comparable<?> generateId() {
        char[] ids = new char[24];
        int epoch = (int) ((System.currentTimeMillis() / 1000) - SYSTEM_START_TIME);
        // 4位字节 ： 时间戳
        for (int i = 7; i >= 0; i--) {
            ids[i] = HEX_UNIT[(epoch & 15)];
            epoch >>>= 4;
        }
        // 5位字节 ： 随机数
        System.arraycopy(MACHINE_CODE, 0, ids, 8, 10);
        // 3位字节： 自增序列。溢出后，相当于从0开始算。
        int seq = SEQUENCE_COUNTER.incrementAndGet();
        for (int i = 23; i >= 18; i--) {
            ids[i] = HEX_UNIT[(seq & 15)];
            seq >>>= 4;
        }
        return new String(ids);
    }

    @Override
    public String supportType() {
        return Type.Mongo_ObjectId.name();
    }

    /**
     * 初始化机器码
     * @return 返回字符数据
     */
    private static char[] initMachineCode() {
        char[] macAndPid = new char[10];
        Random random = new Random();
        for (int i = 9; i >= 0; i--) {
            macAndPid[i] = HEX_UNIT[random.nextInt() & 15];
        }
        return macAndPid;
    }
}
