package qsh.com.animalantiepidemic.security;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class Md5Helper {

    public static String toMd5(String text) throws NoSuchAlgorithmException {

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes("UTF-8"));

            String md5 = new String(messageDigest, "UTF-8");
            return md5;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static final byte[] toMd5Bytes(String s) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(s.getBytes("UTF-8"));

            String md5 = new String(messageDigest, "UTF-8");
            return messageDigest;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
