package org.galaxy.common.serialization.api;

import java.io.IOException;

/**
 * 序列化接口
 * @param <T>
 */
@FunctionalInterface
public interface Serializer<T> {

    byte[] serialize(T obj) throws IOException;

}
