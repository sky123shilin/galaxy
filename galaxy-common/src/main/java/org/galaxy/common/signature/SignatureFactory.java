package org.galaxy.common.signature;


import org.galaxy.util.collection.Tuple2;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface SignatureFactory {

    Tuple2<PublicKey,PrivateKey> createKeyPair();

    PublicKey createPublicKey(String pKey);

    PrivateKey createPrivateKey(String pKey);

    String supportType();

}
