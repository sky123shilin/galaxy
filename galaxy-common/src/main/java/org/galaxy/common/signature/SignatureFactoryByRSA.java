package org.galaxy.common.signature;

import org.galaxy.util.collection.Tuple2;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignatureFactoryByRSA implements SignatureFactory {

    /**
     * 密钥长度，用来初始化
     */
    private static final int KEY_SIZE = 1024;

    @Override
    public Tuple2<PublicKey, PrivateKey> createKeyPair() {
        try {
            // RSA算法要求有一个可信任的随机数源
            SecureRandom secureRandom = new SecureRandom();
            // 为RSA算法创建一个KeyPairGenerator对象
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(supportType());
            // 利用上面的随机数据源初始化这个KeyPairGenerator对象
            keyPairGenerator.initialize(KEY_SIZE, secureRandom);
            // 生成密匙对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 得到公钥
            PublicKey publicKey = keyPair.getPublic();
            String publicKeyBase64 = new BASE64Encoder().encode(publicKey.getEncoded());
            // 得到私钥
            PrivateKey privateKey = keyPair.getPrivate();
            String privateKeyBase64 = new BASE64Encoder().encode(privateKey.getEncoded());
            // 输出公私钥
            System.out.println("publicKeyText:");
            System.out.println(publicKeyBase64.replaceAll("\r|\n", ""));
            System.out.println("privateKeyText:");
            System.out.println(privateKeyBase64.replaceAll("\r|\n", ""));
            // 返回公私钥
            return Tuple2.of(publicKey,privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PublicKey createPublicKey(String pKey) {
        PublicKey publicKey = null;
        try {
            byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(pKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(supportType());
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    @Override
    public PrivateKey createPrivateKey(String pKey) {
        PrivateKey privateKey = null;
        try {
            byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(pKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(supportType());
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    @Override
    public String supportType() {
        return "RSA";
    }
}
