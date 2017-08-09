package com.kanishk.code.bloop.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kanishk on 19/7/17.
 */
public class EncryptUtils {

    public static String a(String input, String key) {
        try {
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher localCipher = Cipher.getInstance("AES");
            localCipher.init(1, localSecretKeySpec);
            return Base64.encodeToString(localCipher.doFinal(input.getBytes("UTF-8")), 0);
        }
        catch (Exception localException) {
            localException.printStackTrace();
        }
        return input;
    }

    public static String b(String input, String key) {
        try {
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher localCipher = Cipher.getInstance("AES");
            localCipher.init(2, localSecretKeySpec);
            return new String(localCipher.doFinal(Base64.decode(input, 0)), "UTF-8");
        }
        catch (Exception localException) {
            localException.printStackTrace();
        }
        return input;
    }
}