package org.galaxy.common.signature;

import org.galaxy.common.signature.SignatureFactory;
import org.galaxy.util.collection.Tuple2;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SignatureFactoryByDSA implements SignatureFactory {

    @Override
    public Tuple2<PublicKey, PrivateKey> createKeyPair() {
        return null;
    }

    @Override
    public PublicKey createPublicKey(String pKey) {
        return null;
    }

    @Override
    public PrivateKey createPrivateKey(String pKey) {
        return null;
    }

    @Override
    public String supportType() {
        return "DSA";
    }
}
