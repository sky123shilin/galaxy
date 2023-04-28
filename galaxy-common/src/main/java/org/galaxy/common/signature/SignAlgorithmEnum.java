package org.galaxy.common.signature;

public enum SignAlgorithmEnum {

    MD5_WITH_RSA("MD5withRSA"),
    SH1_WITH_RSA("SHA1withRSA"),
    SH256_WITH_RSA("SHA256withRSA");

    private final String sign;

    SignAlgorithmEnum(String sign){
        this.sign = sign;
    }
}
