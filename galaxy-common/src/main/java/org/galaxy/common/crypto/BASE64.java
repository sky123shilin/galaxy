package org.galaxy.common.crypto;

/**
 * @author Shilin.Qu
 * @version V1.0
 */
public final class BASE64 {

    /**
     * 使用Base64进行加密
     * @param res 密文
     * @return
     */
    public static String encode(String res) {
        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(res.getBytes());
    }

    /**
     * 使用Base64进行解密
     * @param res
     * @return
     */
    public static String decode(String res) {
        return new String(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(res));
    }

}
