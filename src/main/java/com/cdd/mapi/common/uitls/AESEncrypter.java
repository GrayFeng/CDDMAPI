package com.cdd.mapi.common.uitls;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Charsets;

public class AESEncrypter {

    private static final String key = "ydsw123321";
    
    public static final boolean isDecryption = false;

    public static String encrypt(String str) {
        CryptAES aes = new CryptAES(key, 128);
        byte[] encryptByte = aes.encrypt(str.getBytes(Charsets.UTF_8));
        return new String(Base64.encodeBase64(encryptByte), Charsets.UTF_8);
    }

    public static  String decrypt(String str) {
        CryptAES aes = new CryptAES(key, 128);
        byte[] decrypt = aes.decrypt(Base64.decodeBase64(str));
        return new String(decrypt, Charsets.UTF_8);
    }
    
}
