package org.galaxy.common.serialization.fastjson;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.util.Objects;

@Slf4j
public class FastJsonSerialization<T> implements Serializer<T>, Deserializer<T> {

    /**
     * Fast Json实现的反序列化实现
     * @param bytes 待反序列化的字节数组
     * @param clazz 反序列化的对象class
     * @return 反序列化的对象
     */
    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) {
        if (Objects.isNull(bytes)) {
            return null;
        }
        return JSONObject.parseObject(new String(bytes), clazz);
    }

    /**
     * Fast Json实现的序列化实现
     * @param obj 待序列化的对象
     * @return 返回序列化后的字节数组
     */
    @Override
    public byte[] serialize(T obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        String var1 = JSONObject.toJSONString(obj);
        return var1.getBytes();
    }
}
