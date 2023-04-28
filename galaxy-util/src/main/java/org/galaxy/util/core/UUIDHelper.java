package org.galaxy.util.core;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDHelper {

    private UUIDHelper(){}

    /**
     * UUID转成字节数组
     * @param uuid UUID
     * @return 返回二进制字节数组
     */
    public static byte[] uuidToByteArray(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    /**
     * 字节数组转成UUID
     * @param bytes 字节数组
     * @return 返回UUID对象
     */
    public static UUID byteArrayToUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    /**
     * 字节数组转成UUID的字符串
     * @param bytes 字节数组
     * @return 返回UUID的字符串
     */
    public static String byteArrayToString(byte[] bytes) {
        return byteArrayToUUID(bytes).toString();
    }

    /**
     * 创建一个UUID字符串
     * @return UUID字符串
     */
    public static String of(){
        return UUID.randomUUID().toString();
    }
}
