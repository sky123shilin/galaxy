package org.galaxy.common.serialization.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * protobuf与protostuff关系
 * protobuf是google提供的一种跨语言的数据交换格式，protobuf是一个纯粹的展示层协议，可以和各种传输层协议一起使用
 * protobuf使用广泛，空间开销小且解析性能高，非常适合性能要求高的rpc调用，同时，序列化后数据量非常少，也适应应用层对象的持久化场景
 * 主要问题在于需要编写.proto的IDL文件，需要专门学习语法
 * 使用protobuf的一般步骤如下：
 * 1）配置开发环境，安装protobuf compiler编译器
 * 2）编写.proto文件，定义序列化对象的数据结构
 * 3）基于编写的.proto文件，使用protobuf compiler编译器生成对应的序列化/反序列化工具类
 * 4）基于自动生成的代码，编写自己的序列化应用
 *
 * 对于Java这种具有反射和动态代码生成能力的语言，可以不需要.proto文件进行预编译，可以在代码执行时实现，而protostuff就实现了这个功能
 * ProtoStuff基于google protobuf, protostuff-runtime实现了无需预编译对Java Bean进行protobuf序列化/反序列化的能力
 *
 * @param <T>
 */
public class ProtoStuffSerialization<T> implements Serializer<T>, Deserializer<T> {
    /**
     * 缓存Schema
     */
    private static final Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();
    /**
     * 避免每次序列化都重新申请Buffer空间
     */
    private static final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    /**
     * ProtoStuff的反序列化实现
     * @param bytes 待反序列化的字节数组
     * @param clazz 反序列化的对象class
     * @return 反序列化的对象
     * @throws IOException 异常
     */
    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        Schema<T> schema = getSchema(clazz);
        T obj = schema.newMessage();
        try {
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
            return obj;
        } catch (RuntimeException ex) {
            throw new IOException("Failed to deserialize object type", ex);
        }
    }

    /**
     * ProtoStuff的序列化实现
     * @param obj 待序列化的对象
     * @return 返回序列化后的字节数组
     * @throws IOException 异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public byte[] serialize(T obj) throws IOException {
        Class<T> clazz = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(clazz);
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (RuntimeException ex) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) schemaCache.get(clazz);
        if (Objects.isNull(schema)) {
            schema = RuntimeSchema.getSchema(clazz);
            if (Objects.nonNull(schema)) {
                schemaCache.put(clazz, schema);
            }
        }
        return schema;
    }
}
