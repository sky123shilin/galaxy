package org.galaxy.common.serialization.marshalling;

import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;
import org.jboss.marshalling.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * JBoss Marshalling是一个Java对象序列化包，兼容Java原生的序列化机制
 * 对Java原生序列化机制做了优化，使其在性能上有很大提升，在保持跟java.io.Serializable接口兼容的同时增加了一些可调的参数和附加特性
 * 这些参数和附件特性，可通过工厂类进行配置，对原生java序列化是一个很好的替代
 * @param <T>
 */
public class MarshallingSerialization<T> implements Serializer<T>, Deserializer<T> {

    private static final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
    private static final MarshallingConfiguration configuration = new MarshallingConfiguration();

    static {
        configuration.setVersion(5);
    }

    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
            unmarshaller.start(Marshalling.createByteInput(inputStream));
            T obj = unmarshaller.readObject(clazz);
            unmarshaller.finish();
            return obj;
        } catch (ClassNotFoundException var1) {
            throw new IllegalStateException("Failed to deserialize object type", var1);
        } catch (IOException var2) {
            throw new IOException("Failed to deserialize object", var2);
        }
    }

    @Override
    public byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
            marshaller.start(Marshalling.createByteOutput(outputStream));
            marshaller.writeObject(obj);
            marshaller.flush();
        } catch (IOException ex) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), ex);
        }
        return outputStream.toByteArray();
    }
}
