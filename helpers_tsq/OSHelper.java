package com.app.helpers;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;

import com.ingic.garagediscount.activities.MainActivity;

public class OSHelper {

	public static boolean isExerternalStorageAvailable() {
		return Environment.getExternalStorageState().contentEquals(
				Environment.MEDIA_MOUNTED) ? true : false;
	}

	public static boolean isInternetAvailable(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = conn.getActiveNetworkInfo();
		if (activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static void deleteAppCache(Context context) {
		deleteFiles(context.getCacheDir());
	}

	/**
	 * Deletes Directories after removing all files recursively
	 * 
	 * @param dir
	 *            Directory to clear
	 */
	public static void deleteFiles(File dir) {
		File files[] = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				deleteFiles(dir);
			else
				file.delete();
		}
		dir.delete();
	}

	@TargetApi(11)
	private void disableHardwareAcceleration(Application app, WebView webView) {
		if ((!webView.isHardwareAccelerated())
				&& android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			// Log.e( "HardwareAcceleration", "disable HardwareAcceleration" );
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	public static void placeCall(Fragment fragment, String phoneNumber) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		// tel://
		callIntent.setData(Uri.parse("tel:" + phoneNumber));
		fragment.startActivity(callIntent);
	}

	public static String resolvedMediaPath(Uri mURI, FragmentActivity fragment) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = fragment.managedQuery(mURI, projection, null, null,
				null);
		int column_index_data = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String capturedImageFilePath = cursor.getString(column_index_data);
		return capturedImageFilePath;
	}

	public static String getFileUri(Uri mURI, FragmentActivity fragment) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = fragment.managedQuery(mURI, projection, null, null,
				null);
		int column_index_data = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String capturedImageFilePath = cursor.getString(column_index_data);
		return "file://" + capturedImageFilePath;
	}

	/**
	 * Enables strict mode. This should only be called when debugging the
	 * application and is useful for finding some potential bugs or best
	 * practice violations.
	 */
	@TargetApi(11)
	public static void enableStrictMode() {
		// Strict mode is only available on gingerbread or later
		if (OSHelper.hasGingerbread()) {

			// Enable all thread strict mode policies
			StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyLog();

			// Enable all VM strict mode policies
			StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyLog();

			// Honeycomb introduced some additional strict mode features
			if (OSHelper.hasHoneycomb()) {
				// Flash screen when thread policy is violated
				threadPolicyBuilder.penaltyFlashScreen();
				// For each activity class, set an instance limit of 1. Any more
				// instances and
				// there could be a memory leak.
				vmPolicyBuilder.setClassInstanceLimit(MainActivity.class, 1)
						.setClassInstanceLimit(MainActivity.class, 1);
			}

			// Use builders to enable strict mode policies
			StrictMode.setThreadPolicy(threadPolicyBuilder.build());
			StrictMode.setVmPolicy(vmPolicyBuilder.build());
		}
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * Gingerbread or later.
	 */
	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * Honeycomb or later.
	 */
	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * Honeycomb MR1 or later.
	 */
	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * Honeycomb MR1 or later.
	 */
	public static boolean hasHoneycombMR2() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * ICS or later.
	 */
	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static String getDeviceInfo() {
		String s = "Debug-infos:";
		s += "\n OS Version: " + System.getProperty("os.version") + "("
				+ android.os.Build.VERSION.INCREMENTAL + ")";
		s += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
		s += "\n Device: " + android.os.Build.DEVICE;
		s += "\n Display: " + android.os.Build.DISPLAY;
		s += "\n Has External Storage: " + isExerternalStorageAvailable();
		s += "\n Model (and Product): " + android.os.Build.MODEL + " ("
				+ android.os.Build.PRODUCT + ")";

		return s;

	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	public static String getFileName(String ext) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		long time = now.getTime();
		String fileName = String.valueOf(time) + "." + ext;
		return fileName;
	}

	public static String getFullImagePathAudio(String filename) {
		String[] dirList = getDirList();
		for (int i = 0; i < dirList.length; i++) {
			if (dirList[i].contains("sdcard")) {
				String NewFolder = "/StoryTelling";
				String extStorageDirectory;
				extStorageDirectory = "/mnt/" + dirList[i] + NewFolder;
				File directory = new File(extStorageDirectory
						+ "/AudioStoryRecordings");
				directory.mkdirs();
				String fullImagePath = directory + "/" + filename;// +".jpg"
				// File file = new File( fullImagePath );
				// if(file.mkdirs() || file.isDirectory()){
				return fullImagePath;
				// }
				// return null;
			}
		}
		return null;
	}

	protected static String[] getDirList() {
		File storageDir = new File("/mnt/");
		if (storageDir.isDirectory()) {
			String[] dirList = storageDir.list();
			return dirList;
		}
		return null;
	}

	public static int screenSizeHeight(Activity mContext) {

		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;
		return height;
	}

	public static int screenSizeWidth(Activity mContext) {

		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		return width;
	}
}
