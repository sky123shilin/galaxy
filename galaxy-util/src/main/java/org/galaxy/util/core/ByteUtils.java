package org.galaxy.util.core;

public final class ByteUtils {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private ByteUtils(){}

    /**
     * 将16进制字符串转成字节数据
     * @param var0 16进制字符串
     * @return 返回字节数据
     */
    public static byte[] fromHexString(String var0) {
        char[] var1 = var0.toUpperCase().toCharArray();
        int var2 = 0;
        for (char c : var1) {
            if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F') {
                ++var2;
            }
        }
        byte[] var6 = new byte[var2 + 1 >> 1];
        int var4 = var2 & 1;
        for (char c : var1) {
            if (c >= '0' && c <= '9') {
                var6[var4 >> 1] = (byte) (var6[var4 >> 1] << 4);
                var6[var4 >> 1] = (byte) (var6[var4 >> 1] | c - 48);
            } else {
                if (c < 'A' || c > 'F') {
                    continue;
                }
                var6[var4 >> 1] = (byte) (var6[var4 >> 1] << 4);
                var6[var4 >> 1] = (byte) (var6[var4 >> 1] | c - 65 + 10);
            }
            ++var4;
        }
        return var6;
    }

    /**
     * 转成16进制的字符串
     * @param bytes 字节数组
     * @return 返回字符串
     */
    public static String toHexString(byte[] bytes) {
        char[] chars = new char[bytes.length * 2];
        int i = 0;
        for (byte b : bytes) {
            chars[i++] = HEX_CHARS[b >> 4 & 15];
            chars[i++] = HEX_CHARS[b & 15];
        }
        return new String(chars);
    }

    /**
     * 将字节数组转成二进制字符串
     * @param var0 字节数据
     * @return 二进制字符串
     */
    public static String toBinaryString(byte[] var0) {
        StringBuilder var1 = new StringBuilder();
        for(int var2 = 0; var2 < var0.length; ++var2) {
            byte var3 = var0[var2];
            for(int var4 = 0; var4 < 8; ++var4) {
                int var5 = var3 >>> var4 & 1;
                var1.append(var5);
            }
            if (var2 != var0.length - 1) {
                var1.append(" ");
            }
        }
        return var1.toString();
    }

    /**
     * 字节数组的比较是否相等
     * @param var0 字节数组1
     * @param var1 字节数组2
     * @return 布尔值
     */
    public static boolean equals(byte[] var0, byte[] var1) {
        if (var0 == null) {
            return var1 == null;
        } else if (var1 == null) {
            return false;
        } else if (var0.length != var1.length) {
            return false;
        } else {
            boolean var2 = true;

            for(int var3 = var0.length - 1; var3 >= 0; --var3) {
                var2 &= var0[var3] == var1[var3];
            }

            return var2;
        }
    }

    /**
     * 克隆一份字节数组
     * @param var0 字节数组
     * @return 新的字节数组
     */
    public static byte[] clone(byte[] var0) {
        if (var0 == null) {
            return null;
        } else {
            byte[] var1 = new byte[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static byte int3(int x) {
        return (byte)(x >> 24);
    }

    public static byte int2(int x) {
        return (byte)(x >> 16);
    }

    public static byte int1(int x) {
        return (byte)(x >> 8);
    }

    public static byte int0(int x) {
        return (byte)x;
    }
}
