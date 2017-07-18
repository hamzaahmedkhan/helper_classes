package com.app.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;



public class DeveloperHelper {


	
	

	public static void logTokens(Activity context) {

		// Add code to print out the key hash
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getApplicationContext().getPackageName(),
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(signature.toByteArray());
				Log.d("SHA-KeyHash:::",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));

				md = MessageDigest.getInstance("MD5");
				md.update(signature.toByteArray());
				Log.d("MD5-KeyHash:::",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));

				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("SHA-Hex-From-KeyHash:::", bytesToHex(md.digest()));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
		
		
	}
	
	public static String logKeyHash(Activity context) {
		PackageInfo packageInfo;
		String key = null;
		try {
			//getting application package name, as defined in manifest
			String packageName = context.getApplicationContext().getPackageName();

			//Retriving package info
			packageInfo = context.getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_SIGNATURES);
				
			Log.e("Package Name=", context.getApplicationContext().getPackageName());
			
			for (Signature signature : packageInfo.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				key = new String(Base64.encode(md.digest(), 0));
			
				// String key = new String(Base64.encodeBytes(md.digest()));
				Log.e("Key Hash=", key);
			}
		} catch (NameNotFoundException e1) {
			Log.e("Name not found", e1.toString());
		}
		catch (NoSuchAlgorithmException e) {
			Log.e("No such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}

		return key;
	}
	
	public static String getDeviceUuId(Activity context)
	{
		
		
		String UUID= "";
		String android_id = Secure.getString(context.getApplicationContext()
	            .getContentResolver(), Secure.ANDROID_ID);
	    Log.i("System out", "android_id : " + android_id);

	    final TelephonyManager tm = (TelephonyManager) context.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

	    if(tm!=null)
	    {
	    final String tmDevice, tmSerial, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    Log.i("System out", "tmDevice : " + tmDevice);
	    tmSerial = "" + tm.getSimSerialNumber();
	    Log.i("System out", "tmSerial : " + tmSerial);
	    androidId = ""
	            + android.provider.Settings.Secure.getString(
	            		context.getContentResolver(),
	                    android.provider.Settings.Secure.ANDROID_ID);

	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice
	            .hashCode() << 32)
	            | tmSerial.hashCode());
	    UUID = deviceUuid.toString();
	    Log.i("System out", "UUID : " + UUID);
	    return UUID;
	    }
	    return UUID;
	}
	

	final protected static char[] hexArray = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };


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
	
	
	
	public static boolean isImeiAllowed(Activity context) {

		// 05-15 06:01:51.847: E/DEVICE_IMEI(19164): 358239059743148

		boolean isAllowed;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		 Log.e("DEVICE_IMEI", imei + "");
		if (imei == null)
			imei = "";
		// Log.e("DEVICE ", android.os.Build.DEVICE + "");
		// Log.e("MODEL ", android.os.Build.MODEL + "");
		// Log.e("DISPLAY ", android.os.Build.DISPLAY + "");
		// new FileCache(this).clear();
		if (imei.contains("99000335629867") /* GALAXY TAB DEVICE IMEI */

				|| imei.contains("359871043313478") /* GALAXY TAB DEVICE IMEI */
				|| imei.contains("358465041239263") /* HTC DESIRE S IMEI */
				|| imei.contains("000000000000000") /* EMULATOR's IMEI */
				|| imei.contains("351896051521538") /* SAMSUNG NOTE */
				|| imei.contains("351554054807046") /* SAMSUNG NEXUS */

				|| imei.contains("358465043041436") /* ME */
				|| imei.contains("355300050230478") /* Q Noir A10 #1 */
				|| imei.contains("355300050472179") /* Q Noir A10 #2 */

				|| imei.contains("352618055713229") /* xx */
				|| imei.contains("354784058740771") /*  */

				|| imei.contains("355983059459543") /* SAMSUNG S2 */
				|| imei.contains("359169052116266") /* SAMSUNG S4 */

				|| (imei.contentEquals("")) /* New Emus */

				|| (imei.contentEquals("357376054662435")) /* New Emus */
				|| (imei.contentEquals("357376054662435/01")) /* New Emus */

				|| (imei.contentEquals("355056059213846")) /* New Emus */
				|| (imei.contentEquals("355056059213846/01")) /* New Emus */

				|| (imei.contentEquals("353596053837563"))/* Xperia TX */

				

		) {
			isAllowed = true;
		} else {
			isAllowed = false;
			// ACRA.getErrorReporter().handleSilentException(
			// new Exception("Device Id: " + imei + "  model: "
			// + android.os.Build.MODEL + "  device: "
			// + android.os.Build.DEVICE + ""));
		}
		return isAllowed;
	}



}
