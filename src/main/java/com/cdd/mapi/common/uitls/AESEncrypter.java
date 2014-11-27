package com.cdd.mapi.common.uitls;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Charsets;

public class AESEncrypter {

    private static final String key = "cdd123321CDD";
    
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


    public static  void main(String a[]){
        String str1 = "{\"loginId\":\"13400000004\",\"password\":\"123\"}";
        String str = "PmA1T1Dn+QWIPYPdoinKDWifyv2Tv7LXk7hEFDrIkluOAY0+SHorEuTd+VEA1EQGuBiz97Ddi6pfiClH7yw5HFFkcIwEdwXd/OhNPCXttc5xyTphjU4id+mpximkAHNfKVFLLYhj8NJbPVvQJrVQbw==";
        System.out.println(AESEncrypter.decrypt(str));
        System.out.println(AESEncrypter.encrypt(str1));
    }
}
