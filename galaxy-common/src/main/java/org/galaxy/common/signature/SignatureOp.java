package org.galaxy.common.signature;

import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.util.*;

public class SignatureOp {

    public static volatile SignatureOp so;

    /**
     * 签名字段
     */
    private static final String SIGN = "sign";

    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHM = "SHA256withRSA";

    private SignatureFactory factory = new SignatureFactoryByRSA();

    private SignatureOp(){
        //单例
    }
    //双重锁
    public static SignatureOp getInstance(){
        if (so == null) {
            synchronized (SignatureOp.class) {
                if(so == null){
                    so = new SignatureOp();
                }
            }
        }
        return so;
    }

    public String createSign(Map<String, Object> data, String pKey) {
        try {
            String content = mapToString(data);
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initSign(factory.createPrivateKey(pKey));
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verifySign(Map<String, Object> data,String pKey) {
        try {
            // 原始签名值
            String checkSign = String.valueOf(data.get(SIGN));
            // 需校验的内容
            String content = mapToString(data);
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initVerify(factory.createPublicKey(pKey));
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.getDecoder().decode(checkSign.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String mapToString(Map<String, Object> data) {
        Map<String, Object> treeMap = new TreeMap<>(data);
        StringBuilder sb = new StringBuilder(1024);
        treeMap.forEach((k, v) -> {
            if (!SIGN.equals(k) && Objects.nonNull(v) && ("".equals(String.valueOf(v).trim()))) {
                sb.append(k).append('=').append(v).append('&');
            }
        });
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
