package org.galaxy.common.serialization.api;

import java.io.IOException;

/**
 * 反序列化接口
 * @param <T>
 */
@FunctionalInterface
public interface Deserializer<T> {

    T deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
