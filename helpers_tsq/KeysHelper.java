package com.app.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ahsanmajid on 11/25/2016.
 */

public class KeysHelper {


    final private static char[] hexArray = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void logTokens(Context ctx) {

        // Add code to print out the key hash
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(
                    ctx.getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                Log.d("SHA1-KeyHash:::",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));

                md = MessageDigest.getInstance("MD5");
                md.update(signature.toByteArray());
                Log.d("MD5-KeyHash:::",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("SHA-Hex-From-KeyHash:::", bytesToHex(md.digest()));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
    }
}
