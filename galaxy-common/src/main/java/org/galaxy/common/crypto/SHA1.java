package org.galaxy.common.crypto;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.MessageDigest;

/**
 * SHA1工具类
 * 单向加密，只有encode方法
 */
public final class SHA1 {

    private static final String CHARSET = "utf-8";
    public static final String SHA1 = "SHA1";

    /**
     * MD5加密后，获取的字节数组进行转换成十六进制字符串
     * @param res
     * @return
     */
    public static String encodeAsHex(String res) {
        return messageDigest(res, "Hex");
    }

    /**
     * MD5加密后，获取的字节数组进行base64处理
     * @param res
     * @return
     */
    public static String encodeAsBase64(String res) {
        return messageDigest(res, "Base64");
    }
    /**
     * 使用MessageDigest进行单向加密（无密码）
     * @param res 被加密的文本
     * @param algorithm 加密算法名称
     * @return
     */
    private static String messageDigest(String res,String algorithm){
        try {
            MessageDigest md = MessageDigest.getInstance(SHA1);
            byte[] resBytes = md.digest(res.getBytes(CHARSET));
            return "Base64".equals(algorithm) ? base64(resBytes) : hex(resBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节数组进行base64处理
     * @param res
     * @return
     */
    private static String base64(byte[] res){
        return Base64.encode(res);
    }

    /**
     * 将字节数组进行十六进制转换
     * @param res
     * @return
     */
    private static String hex(byte[] res){
        return String.valueOf(HEX.encode(res));
    }
}
