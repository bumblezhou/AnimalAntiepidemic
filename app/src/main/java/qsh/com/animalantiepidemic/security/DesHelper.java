package qsh.com.animalantiepidemic.security;
import android.util.Log;

import com.loopj.android.http.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class DesHelper {
    public static String encrypt(String plainText, String key){
        String result = null;
        if(plainText == null || plainText.equals("")){
            return null;
        }
        if(key == null || key.equals("")){
            key = "273ece6f97dd844d";
        }

        try {
            byte[] keyBytes = Md5Helper.toMd5Bytes(key);
            byte[] keyBytesToUse;
            if (keyBytes.length == 16) {
                keyBytesToUse = new byte[24];
                System.arraycopy(keyBytes, 0, keyBytesToUse, 0, 16);
                System.arraycopy(keyBytes, 0, keyBytesToUse, 16, 8);
            } else {
                keyBytesToUse = keyBytes;
            }

            Cipher cipher = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
            SecretKeySpec myKey = new SecretKeySpec(keyBytesToUse, "DESede");

            cipher.init(Cipher.ENCRYPT_MODE, myKey);

            try {
                byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

                String encrypted = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
                result = encrypted;

            } catch (IllegalBlockSizeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (BadPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String decrypt(String encryptedText, String key){
        String result = null;
        if(encryptedText == null || encryptedText.equals("")){
            return null;
        }

        if(key == null || key.equals("")){
            key = "273ece6f97dd844d";
        }

        byte[] toDecryptArray = Base64.decode(encryptedText, Base64.DEFAULT);
        try{
            byte[] keyBytes = Md5Helper.toMd5Bytes(key);
            byte[] keyBytesToUse;
            if (keyBytes.length == 16) {
                keyBytesToUse = new byte[24];
                System.arraycopy(keyBytes, 0, keyBytesToUse, 0, 16);
                System.arraycopy(keyBytes, 0, keyBytesToUse, 16, 8);
            } else {
                keyBytesToUse = keyBytes;
            }

            Cipher cipher = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
            SecretKeySpec myKey = new SecretKeySpec(keyBytesToUse, "DESede");

            cipher.init(Cipher.DECRYPT_MODE, myKey);

            try {
                byte[] decryptedBytes = cipher.doFinal(toDecryptArray);

                Charset UTF8_CHARSET = Charset.forName("UTF-8");
                String decryptedText = new String(decryptedBytes, UTF8_CHARSET);
                result = decryptedText;

            } catch (IllegalBlockSizeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (BadPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
