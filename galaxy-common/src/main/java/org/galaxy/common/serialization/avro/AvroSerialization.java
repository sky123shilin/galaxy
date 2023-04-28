package org.galaxy.common.serialization.avro;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.galaxy.common.serialization.api.Deserializer;
import org.galaxy.common.serialization.api.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Avro序列化
 * @param <T>
 */
public class AvroSerialization<T> implements Serializer<T>, Deserializer<T> {

    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        if (! SpecificRecordBase.class.isAssignableFrom(clazz)) {
            throw new UnsupportedOperationException("not support obj type");
        }
        try {
            DatumReader<T> datumReader = new SpecificDatumReader<>(clazz);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(inputStream,null);
            return datumReader.read(clazz.newInstance(), binaryDecoder);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IOException("Failed to deserialize object", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public byte[] serialize(T obj) throws IOException {
        if (!(obj instanceof SpecificRecordBase)) {
            throw new UnsupportedOperationException("not support obj type");
        }
        try {
            DatumWriter<T> datumWriter = new SpecificDatumWriter<T>((Class<T>) obj.getClass());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(outputStream, null);
            datumWriter.write(obj, binaryEncoder);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new IOException("Failed to serialize object of type: " + obj.getClass(), e);
        }
    }
}
