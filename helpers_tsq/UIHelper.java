package com.app.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils.TruncateAt;
import android.text.format.Time;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ingic.garagediscount.R;


public class UIHelper {

	static final int READ_BLOCK_SIZE = 100;


	public static void showLongToastInCenter(Context ctx, int messageId) {
		if(ctx==null) return;
		Toast toast = Toast.makeText(ctx, messageId, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showLongToastInCenter(Context ctx, String message) {
		if(ctx==null) return;
		Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showShortToastInCenter(Context ctx, String message) {
		if(ctx==null) return;
		Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void ShowToast(Context context, String message){
		if(context==null) return;
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public static void ShowToastShort(Context context, String message){
		if(context==null) return;
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showConnectionFailedToast(Context ctx) {
		if(ctx==null) return;
		showLongToastInCenter(ctx, R.string.msg_connection_failed);
	}

	public static void shSowConnectionErrorToast(Context ctx) {
		if(ctx==null) return;
		showLongToastInCenter(ctx, R.string.msg_connection_error);
	}

	public static void hideSoftKeyboard(Context context, EditText editText) {
		if(context==null) return;

		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

	}

	public static void hideSoftKeyboard(Context context, View view) {
		if (context == null)
		{
			return;
		}
		InputMethodManager imm = (InputMethodManager) context

		.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void showAlertDialog(String message, CharSequence title,
			Context context) {
		if(context==null) return;
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

	// public static void showSelectMultiple(Context ctx, BaizaContacts
	// baizaContact){
	// final Context _ctx = ctx;
	// final BaizaContacts _baizaContact = baizaContact;
	//
	// AlertDialog ad = new AlertDialog.Builder(_ctx)
	// .setTitle("Select Phone Number")
	// .setCancelable(false)
	// .setSingleChoiceItems(baizaContact.getPhoneNumbersAsCharSequence(), 0,
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// _baizaContact.setSelectedNumber(which);
	// }
	// })
	// .create();
	// ad.show();
	// }

	public static Rect locateView(View v) {
		int[] loc_int = new int[2];
		if (v == null)
			return null;
		try {
			v.getLocationOnScreen(loc_int);
		} catch (NullPointerException npe) {
			// Happens when the view doesn't exist on screen anymore.
			return null;
		}
		Rect location = new Rect();
		location.left = loc_int[0];
		location.top = loc_int[1];
		location.right = location.left + v.getWidth();
		location.bottom = location.top + v.getHeight();
		return location;
	}

	public static void textMarquee(TextView txtView) {
		// Use this to marquee Textview inside listview

		txtView.setEllipsize(TruncateAt.END);
		// Enable to Start Scroll

		// txtView.setMarqueeRepeatLimit(-1);
		// txtView.setHorizontallyScrolling(true);
		// txtView.setSelected(true);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int getScreenWidth(Activity ctx) {

		Display display = ctx.getWindowManager().getDefaultDisplay();

		if (OSHelper.hasHoneycombMR2()) {
			Point size = new Point();
			display.getSize(size);
			return size.x;
		} else {
			return display.getWidth();
		}

	}

	public static void dimBehind(Dialog dialog) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.9f;
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.setCancelable(false);
	}

	public static void hideSoftKeyboard(Activity activity) {

		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	public static String currentTime() {
		Time now = new Time();
		now.setToNow();
		String current_time = pad(now.year) + "-" + pad(now.month+1) + "-"
				+ pad(now.monthDay) + " " + pad(now.hour) + ":"
				+ pad(now.minute) + ":" + pad(now.second);
		return current_time;

	}

	public static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public static AlertDialog showAlertDialogWithButtons(String questionText,
			String title, String positiveText, String negativeText,
			DialogInterface.OnClickListener dialogListener, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(questionText).setTitle(title).setCancelable(true)
				.setPositiveButton(positiveText, dialogListener)
				.setNegativeButton(negativeText, dialogListener);
		AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public static int getOrientaionFromRotation(int rotation) {
		if (rotation == 90)
			return 6;
		if (rotation == 180)
			return 3;
		if (rotation == 270)
			return 8;
		return 0;
	}

	public static boolean saveImage(Context context, Bitmap bmp, String fileName) {
		boolean file_saved = false;
		try {
			String[] dirList = getDirList();
			for (int i = 0; i < dirList.length; i++) {
				if (dirList[i].contains("sdcard")) {
					String NewFolder = "/Whistle";
					String extStorageDirectory;
					extStorageDirectory = "/mnt/" + dirList[i];
					File mkTarDir = new File(extStorageDirectory + NewFolder);
					if (mkTarDir.isDirectory()) {
						file_saved = saveImage(bmp, fileName, NewFolder,
								extStorageDirectory);
						break;
					} else {
						boolean dir = mkTarDir.mkdir();
						if (dir) {
							if (dir) {
								file_saved = saveImage(bmp, fileName,
										NewFolder, extStorageDirectory);
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

	protected static boolean saveImage(Bitmap bmp, String fileName,
			String NewFolder, String extStorageDirectory)
			throws FileNotFoundException, IOException {
		boolean file_saved;
		OutputStream stream = new FileOutputStream(extStorageDirectory
				+ NewFolder + "/" + fileName);// "+.jpg"
		file_saved = bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		stream.close();
		return file_saved;
	}

	public static String getFileName(String ext) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		long time = now.getTime();
		String fileName = String.valueOf(time) + "." + ext;
		return fileName;
	}

	public static String getFullImagePath(String filename) {
		String[] dirList = getDirList();
		for (int i = 0; i < dirList.length; i++) {
			if (dirList[i].contains("sdcard")) {
				String NewFolder = "/Whistle";
				String extStorageDirectory;
				extStorageDirectory = "/mnt/" + dirList[i] + NewFolder;
				String fullImagePath = extStorageDirectory + "/" + filename;// +".jpg"
				return fullImagePath;
			}

		}
		return null;
	}

	public static boolean deleteAllFiles() {
		File direct = new File(Environment.getExternalStorageDirectory()
				+ "/Whistle");
		if (direct.exists()) {
			for (File c : direct.listFiles()) {
				c.delete();
			}
		}
		return false;
	}


	public void generateNoteOnSD(Context context, String sFileName, String sBody) {
		try {
			File root = new File(Environment.getExternalStorageDirectory(), "Notes");
			if (!root.exists()) {
				root.mkdirs();
			}
			File gpxfile = new File(root, sFileName);
			FileWriter writer = new FileWriter(gpxfile);
			writer.append(sBody);
			writer.flush();
			writer.close();
			Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// write text to file
	public static void WriteFile(Activity context,String value,String fileName) {
		// add-write text into file
		try {

//			OutputStream stream = new FileOutputStream(extStorageDirectory
//					+ NewFolder + "/" + fileName);// "+.jpg"
			FileOutputStream fileout=context.openFileOutput("mytextfile.txt", Context.MODE_PRIVATE);
			OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
			outputWriter.write(value);
			outputWriter.close();

			//display file saved message
			Toast.makeText(context, "File saved successfully!",
					Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read text from file
	public static void ReadFile(Activity context,String fileName) {
		//reading text from file
		try {
			FileInputStream fileIn=context.openFileInput(fileName+".txt");
			InputStreamReader InputRead= new InputStreamReader(fileIn);

			char[] inputBuffer= new char[READ_BLOCK_SIZE];
			String s="";
			int charRead;

			while ((charRead=InputRead.read(inputBuffer))>0) {
				// char to string conversion
				String readstring=String.copyValueOf(inputBuffer,0,charRead);
				s +=readstring;
			}
			InputRead.close();
			Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
