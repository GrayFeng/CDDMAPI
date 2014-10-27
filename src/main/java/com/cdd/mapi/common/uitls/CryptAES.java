package com.cdd.mapi.common.uitls;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptAES {
	
	private Logger log = LoggerFactory.getLogger(CryptAES.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private  SecretKeySpec sKey;

    private byte[] iv = {0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC,
            0xD, 91};

    public CryptAES(String secretkey, int bitLength) {

        byte[] bytes = new byte[bitLength / 8];
        byte[] keys = null;
        try {
            keys = secretkey.getBytes("UTF-8");

            for (int i = 0; i < secretkey.length(); i++) {
                if (i >= bytes.length)
                    break;
                bytes[i] = keys[i];
            }
            sKey = new SecretKeySpec(bytes, "AES");
        } catch (UnsupportedEncodingException e) {
        	log.error("数据加解密异常:"+e.getMessage());
        }

    }

    private byte[] crypt(int mode, byte[] data) {
        int cipherMode;
        switch (mode) {
            case 1:
                cipherMode = Cipher.ENCRYPT_MODE;
                break;
            case 2:
                cipherMode = Cipher.DECRYPT_MODE;
                break;
            default:
                cipherMode = Cipher.ENCRYPT_MODE;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(cipherMode, sKey, ivSpec);
            return cipher.doFinal(data);

        } catch (Exception e) {
        	log.error("数据加解密异常:"+e.getMessage());
        }
        return new byte[0];
    }

    public byte[] encrypt(byte[] data) {
        return crypt(1, data);
    }

    public byte[] decrypt(byte[] encData) {
        return crypt(2, encData);
    }
}
