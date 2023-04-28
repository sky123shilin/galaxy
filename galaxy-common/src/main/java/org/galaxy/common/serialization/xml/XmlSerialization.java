package org.galaxy.common.serialization.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.IOException;
import java.util.Objects;

public class XmlSerialization<T> implements Serializer<T>, Deserializer<T> {
    private static final XStream xstream = new XStream(new DomDriver());

    static {
        XStream.setupDefaultSecurity(xstream);
    }

    /**
     * xml实现的反序列化
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
            xstream.allowTypes(new Class[]{clazz});
            String xml = new String(bytes);
            return clazz.cast(xstream.fromXML(xml));
        } catch (ConversionException | ClassCastException ex) {
            throw new IOException("Failed to deserialize object", ex);
        }
    }

    /**
     * xml实现的序列化
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
            xstream.allowTypes(new Class[]{obj.getClass()});
            return xstream.toXML(obj).getBytes();
        } catch (Exception ex) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), ex);
        }
    }
}
