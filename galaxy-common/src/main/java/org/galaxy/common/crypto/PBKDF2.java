package org.galaxy.common.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * PBKDF2 (Password-Based Key Derivation Function 2)，基于口令的密钥派生函数，可以防止字典攻击和彩虹表攻击。
 * 使用用户持有的、不需要保存的秘密，比如口令，来推导对称密钥(消息的发送方和接收方持有相同的秘密，使用同样的算法来推导对称密钥)。
 */
public final class PBKDF2 {

    private static final String DEFAULT_ALGORITHM = "PBKDF2WithHmacSHA256";

    private static final int DEFAULT_ITERATION_COUNT = 1000;

    private static final int DEFAULT_KEY_SIZE = 256;

    /**
     * 对一段密码即password进行PBKDF2加密
     * 默认算法是PBKDF2WithHmacSHA256
     * @param password 需要加密的密码
     * @return 返回加密后的字符串（十六进制）
     */
    public static String encodeAsHex(char[] password){
        return encodeAsHex(DEFAULT_ALGORITHM,password);
    }
    /**
     * 对一段密码即password进行PBKDF2加密
     * @param algorithm 算法名，比如: PBKDF2WithHmacSHA256
     * @param password 口令，字节数组/字符串
     * @return 返回加密后的字符串（十六进制）
     */
    public static String encodeAsHex(String algorithm, char[] password){
        SecureRandom sRandom = new SecureRandom();
        byte[] salt = new byte[16];
        sRandom.nextBytes(salt);
        return encodeAsHex(algorithm,password,salt,DEFAULT_ITERATION_COUNT,DEFAULT_KEY_SIZE);
    }

    /**
     * 对一段密码即password进行PBKDF2加密
     * @param algorithm 算法名，比如: PBKDF2WithHmacSHA256
     * @param password  口令，字节数组/字符串
     * @param salt 盐，安全生成的随机字节，推荐长度为128位
     * @param iterationCount 迭代次数
     * @param keySize 派生密钥长度
     * @return 返回加密后的字符串（十六进制）
     */
    public static String encodeAsHex(String algorithm, char[] password, byte[] salt, int iterationCount, int keySize){
        KeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keySize);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            byte[] resByte = factory.generateSecret(keySpec).getEncoded();
            return hex(resByte);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
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
