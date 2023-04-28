package org.galaxy.lock.crypto;

/**
 * 十六进制工具类
 * @author Shilin.Qu
 * @version V1.0
 */
public final class HEX {
    private static final char[] HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private HEX() {}

    /**
     * 将字节数组转换为十六进制字符数组
     * @param bytes
     * @return
     */
    public static char[] encode(byte[] bytes) {
        char[] chars = new char[32];

        for(int i = 0; i < chars.length; i += 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX[b >>> 4 & 15];
            chars[i + 1] = HEX[b & 15];
        }

        return chars;
    }

    /**
     * 将十六进制字符数组转换为字节数组
     * @param s
     * @return
     */
    public static byte[] decode(CharSequence s) {
        int nChars = s.length();
        if (nChars % 2 != 0) {
            throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
        } else {
            byte[] result = new byte[nChars / 2];

            for(int i = 0; i < nChars; i += 2) {
                int msb = Character.digit(s.charAt(i), 16);
                int lsb = Character.digit(s.charAt(i + 1), 16);
                if (msb < 0 || lsb < 0) {
                    throw new IllegalArgumentException("Detected a Non-hex character at " + (i + 1) + " or " + (i + 2) + " position");
                }

                result[i / 2] = (byte)(msb << 4 | lsb);
            }

            return result;
        }
    }
}
