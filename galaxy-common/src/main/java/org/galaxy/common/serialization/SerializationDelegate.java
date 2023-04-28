package org.galaxy.common.serialization;

import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;
import org.galaxy.common.serialization.jdk.JDKSerialization;
import org.galaxy.common.serialization.marshalling.MarshallingSerialization;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 序列化委托类，委托一个其他的序列化类来帮忙实现功能
 * @param <T>
 */
public class SerializationDelegate<T> implements Serializer<T>, Deserializer<T> {

    private final Serializer<T> serializer;
    private final Deserializer<T> deserializer;

    public SerializationDelegate(Serializer<T> serializer, Deserializer<T> deserializer) {
        Assert.notNull(serializer, "Serializer must not be null");
        Assert.notNull(deserializer, "Deserializer must not be null");
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        return deserializer.deserialize(bytes,clazz);
    }

    @Override
    public byte[] serialize(T obj) throws IOException {
        return serializer.serialize(obj);
    }

    public Serializer<T> getSerializer() {
        return serializer;
    }

    public Deserializer<T> getDeserializer() {
        return deserializer;
    }

    public static void main(String[] args) throws IOException {
        JDKSerialization<User> serialization = new JDKSerialization<>();
        SerializationDelegate<User> delegate = new SerializationDelegate<>(serialization,serialization);
        User user = User.builder().name("qsl").age(20).height(1.78).build();
        byte[] bytes = delegate.serialize(user);
        System.out.println(new String(bytes));
        User user1 = delegate.deserialize(bytes,User.class);
        System.out.println(user1);
    }
}
