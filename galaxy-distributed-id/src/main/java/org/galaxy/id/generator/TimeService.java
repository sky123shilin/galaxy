package org.galaxy.id.generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Shilin.Qu
 * @version V1.0
 */
public class TimeService {
    /**
     * Get current millis.
     * @return current millis
     */
    public long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Get current date str yyyyMMdd format
     * @return current date str
     */
    public String getCurrentDateStr() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
