package org.galaxy.id.generator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 利用JDK的原子类AtomicInteger来生成自增ID，但不满足分布式的场景
 * 所以这里借助于Redisson的RAtomicLong来实现自增ID，因为Redisson基于Redis来实现的，所以满足分布式场景
 * 且服务重启也不会重新归0
 * 结合时间、随机数、前缀组成唯一的流水号
 * 下面是流水号的结构：
 * OR 20230404 0000001   2364
 * 前缀 时间      自增     随机数
 * 适用于用来生成每天从0开始的流水号，比如各种订单号
 * 关键在于自增部分、随机数部分、自增步数都可自我定制调整
 */
@Component
@Slf4j
public class AtomicIncrementKeyGenerator implements KeyGenerator {
    @Resource
    private RedissonClient redissonClient;
    /**
     * 自增部分的数字长度
     */
    @Setter
    private Integer incrementLength = 7;
    /**
     * 随机数部分的长度
     */
    @Setter
    private Integer randomLength = 2;
    /**
     * 自增部分的步数策略
     */
    @Setter
    private StepStrategy stepStrategy = StepStrategy.RANDOM;
    /**
     * 时间处理类
     */
    @Setter
    private TimeService timeService = new TimeService();

    @Override
    public Comparable<?> generateId() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong("test");
        String currentDay = timeService.getCurrentDateStr();
        String increment = autoLeftZero(atomicLong.getAndAdd(getStep()), incrementLength);
        String randomStr = getRandom(randomLength);
        log.info("Generate Key Success: currentDay = 【{}】, increment = 【{}】, randomStr = 【{}】", currentDay, increment, randomStr);
        return currentDay.concat(increment).concat(randomStr);
    }

    @Override
    public String supportType() {
        return Type.Atomic_Increment.name();
    }

    /**
     * 自动左边补0凑位数
     * @param increment 待处理的long型数据
     * @return 返回字符串
     */
    public String autoLeftZero(Long increment, Integer length) {
        return String.format("%0".concat(length.toString()).concat("d"), increment);
    }

    /**
     * 获取自增部分增长步数（包括随机数策略）
     * @return 步数
     */
    public Long getStep() {
        if (!StepStrategy.RANDOM.equals(stepStrategy)) {
            return stepStrategy.getStep().longValue();
        }
        // 生成随机数自增步数[1,5]
        return ThreadLocalRandom.current().nextLong(1,StepStrategy.RANDOM.getStep());
    }

    /**
     * 生成随机数部分，范围基于给的RandomLength
     * @return 返回随机数
     */
    public String getRandom(Integer length) {
        long max = (long) Math.pow(10, length);
        long random = ThreadLocalRandom.current().nextLong(1, max);
        return autoLeftZero(random, length);
    }

    /**
     * 自增的步数  策略
     * 提供1步、2步、3步、随机步数
     */
    public enum StepStrategy {
        ONE(1),
        TWO(2),
        THREE(3),
        RANDOM(6)
        ;
        private final Integer step;
        StepStrategy(Integer step) {
            this.step = step;
        }
        public Integer getStep() {
            return step;
        }
    }

}
