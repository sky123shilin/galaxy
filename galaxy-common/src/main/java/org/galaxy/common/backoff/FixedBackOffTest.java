package org.galaxy.common.backoff;

import org.springframework.util.Assert;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.FixedBackOff;

/**
 * 固定时间间隔重试
 */
public class FixedBackOffTest {

    public static void main(String[] args) {
        long interval = 100;
        long maxAttempts = 10;

        BackOff backOff = new FixedBackOff(interval,maxAttempts);
        BackOffExecution execution = backOff.start();

        for (int i = 0; i < 10; i++) {
            System.out.println(execution.nextBackOff());
        }
    }
}
