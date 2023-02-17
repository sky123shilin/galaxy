package org.galaxy.util.core;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * UUID工具类
 */
public class UuidUtils {

    public static byte[] uuidToByteArray(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID byteArrayToUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public static String byteArrayToString(byte[] bytes) {
        return byteArrayToUUID(bytes).toString();
    }

    public static String create(){
        return UUID.randomUUID().toString();
    }
}
