package org.galaxy.common.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * Kryo的Input和Output接收一个InputStream和OutputStream，Kryo通常完成字节数组和对象的转换，所以常用的输入输出流实现为ByteArrayInputStream/ByteArrayOutputStream。
 * Kryo共支持三种读写方式:
 * 1) 如果知道class字节码，并且对象不为空
 * kryo.writeObject(output, someObject);
 * SomeClass someObject = kryo.readObject(input, SomeClass.class);
 * 2) 如果知道class字节码，并且对象可能为空
 * kryo.writeObjectOrNull(output, someObject);
 * SomeClass someObject = kryo.readObjectOrNull(input, SomeClass.class);
 * 3) 如果实现类的字节码未知，并且对象可能为null
 * kryo.writeClassAndObject(output, object);
 * Object object = kryo.readClassAndObject(input);
 * @param <T>
 */
public class KryoSerialization<T> implements Serializer<T>, Deserializer<T> {

    /**
     * 使用ThreadLocal维护Kryo实例，这样减少了每次使用都实例化一次Kryo的开销又可以保证其线程安全。
     * Kryo对象是线程不安全的
     */
    private static final ThreadLocal<Kryo> kryoLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(true);//默认值为true,强调作用
        kryo.setRegistrationRequired(false);//默认值为false,强调作用
        return kryo;
    });

    /**
     * Kryo的反序列化实现
     * @param bytes 待反序列化的字节数组
     * @param clazz 反序列化的对象class
     * @return 反序列化的对象
     * @throws IOException 异常
     */
    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        Kryo kryo = kryoLocal.get();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            Input input = new Input(bis);
            input.close();
            return clazz.cast(kryo.readClassAndObject(input));
        } catch (KryoException ex) {
            throw new IOException("Failed to deserialize object", ex);
        }
    }

    /**
     * Kryo的序列化实现
     * @param obj 待序列化的对象
     * @return 返回序列化后的字节数组
     * @throws IOException 异常
     */
    @Override
    public byte[] serialize(T obj) throws IOException {
        if (Objects.isNull(obj)) {
            return null;
        }
        Kryo kryo = kryoLocal.get();
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Output output = new Output(bos);
            kryo.writeClassAndObject(output, obj);
            output.close();
            return bos.toByteArray();
        } catch (KryoException ex) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), ex);
        }
    }
}
