package org.galaxy.common.serialization.hessian2;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Hessian2Serialization<T> implements Serializer<T>, Deserializer<T> {

    /**
     * Hessian2的反序列化实现
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
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            Hessian2Input var1 = new Hessian2Input(input);
            Throwable var2 = null;
            T var3;
            try {
                var3 = clazz.cast(var1.readObject());
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
        } catch (IOException var3) {
            throw new IOException("Failed to deserialize object", var3);
        } catch (ClassCastException var4) {
            throw new IllegalStateException("Failed to deserialize object type", var4);
        }
    }

    /**
     * Hessian2的序列化实现
     * @param obj 待序列化的对象
     * @return 返回序列化后的字节数组
     * @throws IOException 异常
     */
    @Override
    public byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        try {
            Hessian2Output var1 = new Hessian2Output(output);
            Throwable var3 = null;
            try {
                var1.writeObject(obj);
                var1.flush();
            } catch (Throwable var13) {
                var3 = var13;
                throw var13;
            } finally {
                if (var3 != null) {
                    try {
                        var1.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    var1.close();
                }
            }
        } catch (IOException var15) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), var15);
        }
        return output.toByteArray();
    }
}
