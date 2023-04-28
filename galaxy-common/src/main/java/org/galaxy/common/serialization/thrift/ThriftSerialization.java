package org.galaxy.common.serialization.thrift;

import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.IOException;
import java.util.Objects;

/**
 * Thrift实现的序列化，反序列化
 * 对象必须是TBase的子类对象
 * Thrift是一个RPC框架，其实也可以单独使用Thrift进行数据的序列化/反序列化操作
 * 使用Thrift之前，需要编写以.thrift结尾的IDL文件，再使用Thrift提供的编译器编译生成对应的代码，对Java而言，所有生成的Java Bean都继承了类org.apache.thrift.TBase
 * 使用Thrift的序列化机制，步骤如下：
 * 1.编写所需要的序列化数据结构的.thrift文件
 * 2.使用Thrift提供的代码生成工具，生成.thrift文件对应的Java类
 * 3.使用Thrift提供的TSerializer和TDeserializer分别进行序列化操作和反序列化操作
 * Thrift支持多种序列化协议，常用的有TBinaryProtocol(二进制协议)、TCompactProtocol(字节压缩的二进制协议)、TJSONProtocol(JSON协议)
 * @param <T>
 */
public class ThriftSerialization<T> implements Serializer<T>, Deserializer<T> {

    /**
     * Thrift的反序列化实现
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

        if (!TBase.class.isAssignableFrom(clazz)) {
            throw new UnsupportedOperationException("deserialize not support obj type");
        }

        try {
            TBase<?,?> tBase = (TBase<?, ?>) clazz.newInstance();
            TDeserializer tDeserializer = new TDeserializer(new TBinaryProtocol.Factory());
            tDeserializer.deserialize(tBase, bytes);
        } catch (InstantiationException | IllegalAccessException | TException ex) {
            throw new IOException("Failed to deserialize object", ex);
        }

        return null;
    }

    /**
     * Thrift的序列化实现
     * @param obj 待序列化的对象
     * @return 返回序列化后的字节数组
     * @throws IOException 异常
     */
    @Override
    public byte[] serialize(T obj) throws IOException {
        if (Objects.isNull(obj)) {
            return null;
        }
        if(!(obj instanceof TBase)) {
            throw new UnsupportedOperationException("serialize not support obj type");
        }
        try {
            TSerializer tSerializer = new TSerializer(new TBinaryProtocol.Factory());
            return tSerializer.serialize((TBase<?,?>) obj);
        } catch (TException ex) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), ex);
        }
    }
}
