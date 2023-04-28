package org.galaxy.common.crypto;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.util.DigestUtils;
import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * 单向加密，只有encode方法
 */
public final class MD5 {

    private static final String CHARSET = "utf-8";
    public static final String MD5 = "MD5";

    /**
     * MD5加密后，获取的字节数组进行转换成十六进制字符串
     * @param res
     * @return
     */
    public static String encodeAsHex(String res) {
        return digest(res, "Hex");
    }

    /**
     * 使用Spring自带的DigestUtils工具类来实现MD5加密
     * @param res
     * @return
     */
    public static String encodeAsHexBySpring(String res){
        return DigestUtils.md5DigestAsHex(res.getBytes());
    }

    /**
     * MD5加密后，获取的字节数组进行base64处理
     * @param res
     * @return
     */
    public static String encodeAsBase64(String res) {
        return digest(res, "Base64");
    }
    /**
     * 使用MessageDigest进行单向加密（无密码）
     * @param res 被加密的文本
     * @param algorithm 加密算法名称
     * @return
     */
    private static String digest(String res,String algorithm){
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] resBytes = md.digest(res.getBytes(CHARSET));
            return "base64".equals(algorithm) ? base64(resBytes) : hex(resBytes);
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
