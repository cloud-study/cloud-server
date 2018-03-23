package com.cloud.authserver;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/28 15:18
 * @description
 */
public class TestJwtToken {

    public static final String secret = "kPYkmPZLEzUcKYbuza2svliaUdAa";
    @Test
    public void testToken(){
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTk4NDY0NDQsInRlc3QiOiJ0ZXN0IiwidXNlcl9uYW1lIjoidGVhY2hlciIsImp0aSI6ImRkNTA1ZTYzLTgyMGUtNGZkOS1iNzA0LTIzMjhlMTJjM2Y4MSIsImNsaWVudF9pZCI6ImhJUVJTNm1mMUpOR2FfTDBUbjNtendtdTFSOGEiLCJzY29wZSI6WyJvcGVuaWQiXX0.DG-TuwdZNfchdn32CjmlNYGRBWd__DjJE1PACpwl5uZYWtNF6edN_JtDXyMOqc2zxI6ImSE5iQE_lCMHWeBa8_9QSH-pWpkVaApL4j-AEkwDNCny4T1P8DDBloS_95DnYAfbaZAEdwwqxhN9dHbFgMW8n5J2p42jL9ROBsOzT_BncD1Lu2vcHXLerpbgP0r1Wv9sF0PEcriivIMVDXLQRiaCl27oXNqHxB_GtXQdoseFYVYpTMrmc9eCkUpkky_L0Cl-e8FCuQ-iS6Ejd8Q1PXiX6-gv5JuMcf_uDWQUBPAtwDxdvAr4cN2Q4DcZBVLUcb1YQVbV2wPf5GpBZm5BQA";
        Jwt jwt = JwtHelper.decode(token);
        System.out.println(jwt);
    }

    @Test
    public void publicKey(){
        Resource resource = new ClassPathResource("jwt.jks");
        KeyPair keyPair = new KeyStoreKeyFactory(resource, secret.toCharArray()).getKeyPair("jwt");
        RSAPublicKey key = (RSAPublicKey)keyPair.getPublic();
        System.out.println(key);
        String verifierKey = "-----BEGIN PUBLIC KEY-----\n" + new String(Base64.encode(key.getEncoded()))
                + "\n-----END PUBLIC KEY-----";
        System.out.println(verifierKey);
    }
}
