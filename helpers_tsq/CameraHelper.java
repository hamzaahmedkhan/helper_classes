package com.app.helpers;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class CameraHelper {


	
	public static String createImageFile(Bitmap img ,final Context context) {
		// Create an image file name

		String ImageUrl = "";
		File image = null;

		try {
			String extStorageDirectory = Environment
					.getExternalStorageDirectory().toString();
			// Creating an internal dir;
			image = new File(extStorageDirectory, "myfile"); // Getting a file
																// within the
																// dir.
			FileOutputStream out = new FileOutputStream(image);
			img.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(context,
					"Image can't be taken due to low memory.",
					Toast.LENGTH_LONG).show();
		}

		// Save a file: path for use with ACTION_VIEW intents
		if (image != null)
			return ImageUrl = "file:" + image.getAbsolutePath();
		else
			return ImageUrl;
	}
	
	
	
	
	
	public static String getRealPathFromURI(Uri contentUri,final Context context) {
		try {
			int column_index;
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(
					contentUri, proj, null, null, null);
			if (cursor != null) {
				column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
			} else {
				cursor = context.getContentResolver().query(contentUri,
						null, null, null, null);
				cursor.moveToFirst();
				column_index = cursor
						.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			}

			return cursor.getString(column_index);
		} catch (Exception e) {
			return contentUri.getPath();
		}
	}

	
}
