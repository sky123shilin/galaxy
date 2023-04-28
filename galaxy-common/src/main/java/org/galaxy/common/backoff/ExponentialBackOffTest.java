package org.galaxy.common.backoff;

import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.ExponentialBackOff;

/**
 * 指数时间间隔重试
 * 常用于重试逻辑中，用于计算下一次重试间隔
 */
public class ExponentialBackOffTest {

    public static void main(String[] args) {
        long initialInterval = 100;//初始间隔
        long maxInterval = 5 * 1000L;//最大间隔
        long maxElapsedTime = 50 * 1000L;//最大时间间隔
        double multiplier = 1.5;//递增倍数（即下次间隔是上次的多少倍）
        ExponentialBackOff backOff = new ExponentialBackOff(initialInterval, multiplier);
        backOff.setMaxInterval(maxInterval);
        //currentElapsedTime = interval1 + interval2 + ... + intervalN;
        backOff.setMaxElapsedTime(maxElapsedTime);
        BackOffExecution execution = backOff.start();
        for(int i = 1; i <= 18; i++) {
            System.out.println(execution.nextBackOff());
         }
    }
}
