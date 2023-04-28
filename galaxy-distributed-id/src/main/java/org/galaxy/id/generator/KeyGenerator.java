package org.galaxy.id.generator;

public interface KeyGenerator {

    /**
     * Generate key.
     * @return generated key
     */
    Comparable<?> generateId();

    /**
     * Get algorithm type.
     * @return type
     */
    String supportType();

    /**
     * 定义支持的分布式ID类型枚举
     */
    enum Type {
        Atomic_Increment,
        Snowflake,
        Leaf,
        Redis_Increment,
        Mysql_Batch,
        UUID,
        Mongo_ObjectId
    }

}
