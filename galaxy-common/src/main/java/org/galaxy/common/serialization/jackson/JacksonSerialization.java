package org.galaxy.common.serialization.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class JacksonSerialization<T> implements Serializer<T>, Deserializer<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    /**
     * Jackson实现的反序列化实现
     * @param bytes 待反序列化的字节数组
     * @param clazz 反序列化的对象class
     * @return 反序列化的对象
     * @throws IOException 异常
     */
    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        if (Objects.isNull(bytes)) {
            return null;
        }
        try {
            return objectMapper.reader().
                    forType(clazz).
                    readValue(new String(bytes));
        } catch (JsonProcessingException var2) {
            throw new IOException("Failed to deserialize object of type", var2);
        }
    }

    /**
     * Jackson实现的序列化实现
     * @param obj 待序列化的对象
     * @return 返回序列化后的字节数组
     * @throws IOException 异常
     */
    @Override
    public byte[] serialize(T obj) throws IOException {
        if (Objects.isNull(obj)) {
            return null;
        }
        try {
            String var1 = objectMapper.writeValueAsString(obj);
            return var1.getBytes();
        } catch (JsonProcessingException var2) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), var2);
        }
    }
}
