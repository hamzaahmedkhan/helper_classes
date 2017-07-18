package com.app.helpers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.widget.Toast;

public class Funcs {
	static int MIN_RSSI = -100;
	static int MAX_RSSI = -55;
	public static int socialcount = 3;

	public static void showShortToast(CharSequence message, Context context) {

		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,
				0, 0);
		toast.show();

	}

	public static void showLongToast(CharSequence message, Context context) {

		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,
				0, 0);
		toast.show();
	}

	public static void showAlertDialog(String message, CharSequence title,
			Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setTitle(title).setCancelable(true)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showAlertDialogPositiveButton(String message,
			CharSequence title, Context context, String positvieText,
			DialogInterface.OnClickListener dialogListener,
			DialogInterface.OnClickListener dialogListener2) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setTitle(title).setCancelable(true)
				.setPositiveButton(positvieText, dialogListener)
				.setNegativeButton("No", dialogListener2);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static ProgressDialog getProgressDialog(Context context) {
		return ProgressDialog.show(context, "", "Loading..");
	}

	public static void showAlertDialogWithButtons(String questionText,
			String positiveText, String negativeText,
			DialogInterface.OnClickListener dialogListener, Context context,
			CharSequence title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(questionText).setCancelable(false).setTitle(title)
				.setPositiveButton(positiveText, dialogListener)
				.setNegativeButton(negativeText, null);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showAlertDialogWithButtonsTitle(String questionText,
			String positiveText, String negativeText, String title,
			DialogInterface.OnClickListener dialogListener, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(questionText).setCancelable(false)
				.setPositiveButton(positiveText, dialogListener)
				.setNegativeButton(negativeText, dialogListener)
				.setTitle(title);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showAlertDialogWithButtons(String questionText,
			String positiveText, String neutralText, String negativeText,
			DialogInterface.OnClickListener dialogListener, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(questionText).setCancelable(false)
				.setPositiveButton(positiveText, dialogListener)
				.setNeutralButton(neutralText, dialogListener)
				.setNegativeButton(negativeText, dialogListener);
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static boolean isSDPresent() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public static HashMap<String, Object> castObjectToHashMap(Object obj) {
		HashMap<String, Object> hashMap = null;
		if (obj instanceof Map<?, ?>) {
			hashMap = (HashMap<String, Object>) obj;
		}
		return hashMap;
	}

	public static ArrayList<HashMap<String, Object>> castObjectToArrayListOfHashMaps(
			Object obj) {
		ArrayList<HashMap<String, Object>> arrayList = null;
		if (obj instanceof List<?>) {
			arrayList = (ArrayList<HashMap<String, Object>>) obj;
		}
		return arrayList;
	}

	public static String createEqualsToString(String field, String value) {
		field = field.replaceAll(" ", "");
		StringBuilder stringBuilder = new StringBuilder(Funcs.quoteText(field));
		stringBuilder.append(":");
		stringBuilder.append(Funcs.quoteText(value));
		return stringBuilder.toString();
	}

	public static String quoteText(String text) {
		StringBuilder sb = new StringBuilder("\"");
		sb.append(text);
		sb.append("\"");
		return sb.toString();
	}

	public static String createEqualsToStringboolean(String field, boolean b) {
		// TODO Auto-generated method stub
		field = field.replaceAll(" ", "");
		StringBuilder stringBuilder = new StringBuilder(Funcs.quoteText(field));
		stringBuilder.append(":");
		stringBuilder.append(b);
		return stringBuilder.toString();

	}

	public static String getRelativeTime(String time) {
		Timestamp timeStamp = Timestamp.valueOf(time);
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp currentTimestamp = new Timestamp(now.getTime());
		return DateUtils.getRelativeTimeSpanString(timeStamp.getTime(),
				currentTimestamp.getTime(), DateUtils.MINUTE_IN_MILLIS,
				DateUtils.FORMAT_ABBREV_RELATIVE).toString();
	}

	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Z");
		String timeStamp = String.valueOf(now.getTime());
		String value = simpleDateFormat.format(now);
		String jsonDate = "/Date(" + timeStamp + value + ")/";
		return jsonDate;
	}

	public static String stipHtml(String html) {
		return Html.fromHtml(html).toString();
	}

	public static String extractEmail(String content) {
		String email = "";
		String regex = "(\\w+)(\\.\\w+)*@(\\w+\\.)(\\w+)(\\.\\w+)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			email = matcher.group();

			if (!isValidEmailAddress(email)) {
				email = "";
			}

			break;
		}
		return email;
	}

	public static boolean isValidEmailAddress(String emailAddress) {
		String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = emailAddress;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();

	}
	
	public static boolean isEmailValid(String email) {
		if (email == null)
			return false;
		else
			return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	public static String extractPhonenumber(String html) {
		int i = 0;
		char ch;
		String temp = "";
		while (i < html.length()) {
			ch = html.charAt(i);
			if ((ch >= '0' && ch <= '9') || ch == '+') {

				temp += ch;

			}
			i++;
		}
		return temp;
	}

	public static String getFileName(String ext) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		long time = now.getTime();
		String fileName = String.valueOf(time) + "." + ext;
		return fileName;
	}

	public static String extractUsername(String html) {
		int i = 0;
		char ch;
		String temp = "";
		while (i < html.length()) {
			ch = html.charAt(i);
			if (ch == '/') {
				while (ch != ' ') {
					temp += ch;
					if (i < html.length())
						ch = html.charAt(i++);
					else
						break;
				}
			} else {
				if (i < html.length())
					ch = html.charAt(i++);
			}

		}
		return temp;
	}

	public static Bitmap getImageBitmapFromUrl(URL url) {
		Bitmap bm = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200) {
				return bm;
			}
			conn.connect();
			InputStream is = conn.getInputStream();

			BufferedInputStream bis = new BufferedInputStream(is);
			try {
				bm = BitmapFactory.decodeStream(bis);
			} catch (OutOfMemoryError ex) {
				bm = null;
			}
			bis.close();
			is.close();
		} catch (Exception e) {
		}

		return bm;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean testEmpty(String str) {
		if ((str == null) || str.matches("^\\s*$")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isValidUserName(String callName) {

		Pattern p = Pattern.compile("[a-zA-Z0-9-_]+");
		Matcher m = p.matcher(callName);
		boolean matchFound = m.matches();

		return matchFound;
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {

		Pattern p = Pattern.compile("[0-9+-.]+");
		Matcher m = p.matcher(phoneNumber);
		boolean matchFound = m.matches();

		return matchFound;
	}

	public static String makeJsonKeyValue(String key, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(Funcs.quoteText(key));
		sb.append(":");
		sb.append(Funcs.quoteText(value));
		return sb.toString();
	}

	public static String getValuefromJson(JSONObject jsonUserData, String key) {
		// TODO Auto-generated method stub
		String user_id = "";
		try {
			user_id = jsonUserData.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user_id;
	}

	public static JSONObject getJsonObjectfromText(String contentText) {
		// TODO Auto-generated method stub
		JSONObject json_obj = null;
		try {
			json_obj = new JSONObject(contentText);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json_obj;
	}

	public static JSONObject getJsonObjectfromJson(JSONObject JsonObj,
			String key) {
		// TODO Auto-generated method stub
		JSONObject json_obj = null;
		try {
			json_obj = JsonObj.getJSONObject(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json_obj;
	}

	public static boolean matchAphabets(String new_response) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("[a-zA-Z]");
		Matcher m = p.matcher(new_response);

		if (m.find())
			return true;
		else
			return false;
	}

	public static int calculateSignalLevel(int rssi, int numLevels) {
		if (rssi <= MIN_RSSI) {
			return 0;
		} else if (rssi >= MAX_RSSI) {
			return numLevels - 1;
		} else {
			int partitionSize = (MAX_RSSI - MIN_RSSI) / (numLevels - 1);
			return (rssi - MIN_RSSI) / partitionSize;
		}
	}

	public static String getMonthShortName(int monthNumber) {
		String monthName = "";

		if (monthNumber >= 0 && monthNumber < 12)
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MONTH, monthNumber);

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
				simpleDateFormat.setCalendar(calendar);
				monthName = simpleDateFormat.format(calendar.getTime());
			} catch (Exception e) {
				if (e != null)
					e.printStackTrace();
			}
		return monthName;
	}

	public static int GetDipsFromPixel(Context context, float pixels) {
		// Get the screen's density scale
		final float scale = context.getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public static boolean checkInternetConnection(Context context) {
		if (isInternetReachable(context
				.getSystemService(context.CONNECTIVITY_SERVICE))) {
			return true;
		} else {
			Funcs.showShortToast("Internet Connection not available", context);
			return false;
		}
	}

	public static boolean isInternetReachable(Object connectivityService) {

		ConnectivityManager cm = (ConnectivityManager) connectivityService;
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static boolean saveImage(Context context, Bitmap bmp, String userId) {
		boolean file_saved = false;
		try {
			String[] dirList = getDirList();
			for (int i = 0; i < dirList.length; i++) {
				if (dirList[i].contains("sdcard")) {
					String NewFolder = "/Tipriciate";
					String extStorageDirectory;
					extStorageDirectory = "/mnt/" + dirList[i];
					File mkTarDir = new File(extStorageDirectory + NewFolder);
					if (mkTarDir.isDirectory()) {
						file_saved = saveImage(bmp, userId, NewFolder,
								extStorageDirectory);
						break;
					} else {
						boolean dir = mkTarDir.mkdir();
						if (dir) {
							if (dir) {
								file_saved = saveImage(bmp, userId, NewFolder,
										extStorageDirectory);
								break;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file_saved;
	}

	protected static String[] getDirList() {
		File storageDir = new File("/mnt/");
		if (storageDir.isDirectory()) {
			String[] dirList = storageDir.list();
			return dirList;
		}
		return null;
	}

	protected static boolean saveImage(Bitmap bmp, String userId,
			String NewFolder, String extStorageDirectory)
			throws FileNotFoundException, IOException {
		boolean file_saved;
		OutputStream stream = new FileOutputStream(extStorageDirectory
				+ NewFolder + "/" + userId);// "+.jpg"
		file_saved = bmp.compress(CompressFormat.JPEG, 50, stream);
		stream.close();
		return file_saved;
	}

	public static boolean deleteFile(String filename) {
		String fullImagePath;
		// String folder = "/Poice";
		// fullImagePath =
		// Environment.getExternalStorageDirectory().toString()+folder+"/"+filename;
		File file = new File(filename);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	public static Bitmap getImage(String filename) {
		String[] dirList = getDirList();
		for (int i = 0; i < dirList.length; i++) {
			if (dirList[i].contains("sdcard")) {
				String NewFolder = "/Poice";
				String extStorageDirectory;
				extStorageDirectory = "/mnt/" + dirList[i] + NewFolder;
				String fullImagePath = extStorageDirectory + "/" + filename;// +".jpg"
				File file = new File(fullImagePath);
				if (file.exists()) {
					return BitmapFactory.decodeFile(fullImagePath);
				} else {

				}
			}
		}
		return null;
	}

	public static String getFullImagePath(String filename) {
		String[] dirList = getDirList();
		for (int i = 0; i < dirList.length; i++) {
			if (dirList[i].contains("sdcard")) {
				String NewFolder = "/Poice";
				String extStorageDirectory;
				extStorageDirectory = "/mnt/" + dirList[i] + NewFolder;
				String fullImagePath = extStorageDirectory + "/" + filename;// +".jpg"
				return fullImagePath;
			}

		}
		return null;
	}

	public static void addImageParamsExtra(Intent intent, Uri u) {
		intent.putExtra("outputX", 500);
		intent.putExtra("outputY", 500);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
	}

	@SuppressWarnings("deprecation")
	public static String getPath(Uri uri, Activity c) {
		String selectedImagePath;
		// 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = c.managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			selectedImagePath = cursor.getString(column_index);
		} else {
			selectedImagePath = null;
		}

		if (selectedImagePath == null) {
			// 2:OI FILE Manager --- call method: uri.getPath()
			selectedImagePath = uri.getPath();
		}
		return selectedImagePath;
	}

	public static String getPathForVideo(Uri uri, Activity c) {
		String selectedImagePath;
		// 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
		String[] projection = { MediaStore.Video.Media.DATA };
		Cursor cursor = c.managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			selectedImagePath = cursor.getString(column_index);
		} else {
			selectedImagePath = null;
		}

		if (selectedImagePath == null) {
			// 2:OI FILE Manager --- call method: uri.getPath()
			selectedImagePath = uri.getPath();
		}
		return selectedImagePath;
	}

	public static void createImageDirectory() {

		File Directory = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/iTravel/");
		if (!Directory.exists()) {
			Directory.mkdirs();
		}
	}

	public static String replace(Activity activity, Bitmap bmp, String paths) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, 70, bytes);
		String path = paths;
		File file = new File(path);
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			ostream.write(bytes.toByteArray());
			ostream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

}
