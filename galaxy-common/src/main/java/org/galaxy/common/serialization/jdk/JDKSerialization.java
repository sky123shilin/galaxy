package org.galaxy.common.serialization.jdk;

import lombok.extern.slf4j.Slf4j;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.*;
import java.util.Objects;

@Slf4j
public class JDKSerialization<T> implements Serializer<T>, Deserializer<T> {

    /**
     * JDK自带的反序列化实现
     * @param bytes 待反序列化的字节数组
     * @param clazz 反序列化的对象class
     * @return 反序列化的对象
     * @throws IOException 异常
     */
    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        if (bytes == null) {
            return null;
        } else {
            try {
                ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
                Throwable var2 = null;
                T var3;
                try {
                    var3 = clazz.cast(input.readObject());
                } catch (Throwable var14) {
                    var2 = var14;
                    throw var14;
                } finally {
                    if (var2 != null) {
                        try {
                            input.close();
                        } catch (Throwable var13) {
                            var2.addSuppressed(var13);
                        }
                    } else {
                        input.close();
                    }
                }
                return var3;
            } catch (IOException var16) {
                throw new IOException("Failed to deserialize object", var16);
            } catch (ClassNotFoundException | ClassCastException var17) {
                throw new IllegalStateException("Failed to deserialize object type", var17);
            }
        }
    }

    /**
     * JDK自带的序列化实现
     * @param obj 待序列化的对象
     * @return 返回序列化后的字节数组
     * @throws IOException 异常
     */
    @Override
    public byte[] serialize(T obj) throws IOException {
        if (Objects.isNull(obj)) {
            return null;
        } else {
            ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(output);
                Throwable var3 = null;
                try {
                    oos.writeObject(obj);
                    oos.flush();
                } catch (Throwable var13) {
                    var3 = var13;
                    throw var13;
                } finally {
                    if (var3 != null) {
                        try {
                            oos.close();
                        } catch (Throwable var12) {
                            var3.addSuppressed(var12);
                        }
                    } else {
                        oos.close();
                    }
                }
            } catch (IOException var15) {
                throw new IOException("Failed to serialize object of type: " + obj.getClass(), var15);
            }
            return output.toByteArray();
        }
    }
}
