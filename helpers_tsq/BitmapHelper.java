package com.app.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

public class BitmapHelper {

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

	public static Bitmap getImageOrientation(String _path, Bitmap bitmap) {
		ExifInterface exif = null;
		Bitmap bmp = null;
		try {
			exif = new ExifInterface(_path);

			int exifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			int rotate = 0;

			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = -90;
				break;

			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = -180;
				break;

			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = -270;
				break;

			case ExifInterface.ORIENTATION_NORMAL:
				rotate = 0;
				break;
			}
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			if (rotate != 0) {

				Matrix mtx = new Matrix();
				mtx.setRotate(rotate);
				mtx.preRotate(rotate);
				mtx.postRotate(rotate);

				// Rotating Bitmap & convert to ARGB_8888, required by tess
				// if(rotate == 0)
				// bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, null, true);
				// else
				bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
				// bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
				return bmp;
			} else {
				bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, null, true);
				return bmp;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static Bitmap scaleCenterCrop(Bitmap source, int newHeight,
			int newWidth) {
		int sourceWidth = source.getWidth();
		int sourceHeight = source.getHeight();
		float xScale = (float) newWidth / sourceWidth;
		float yScale = (float) newHeight / sourceHeight;
		float scale = Math.max(xScale, yScale);

		// get the resulting size after scaling
		float scaledWidth = scale * sourceWidth;
		float scaledHeight = scale * sourceHeight;

		// figure out where we should translate to
		float dx = (newWidth - scaledWidth) / 2;
		float dy = (newHeight - scaledHeight) / 2;

		Bitmap dest = Bitmap.createBitmap(newWidth, newHeight,
				source.getConfig());
		Canvas canvas = new Canvas(dest);
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		matrix.postTranslate(dx, dy);
		canvas.drawBitmap(source, matrix, null);
		return dest;
	}

	public static String replace(Activity activity, Bitmap bmp, String paths) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
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
		return /* (path.contains("png")) ? path.replace("png", "jpg") : */path;
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

//	public static float getScreenSize(Activity activity) {
//
//		DisplayMetrics metrics = new DisplayMetrics();
//
//		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//		float height = metrics.heightPixels / metrics.xdpi;
//		float width = metrics.widthPixels / metrics.ydpi;
//
//		return FloatMath.sqrt(height * height + width * width);
//	}

	public static Bitmap getRoundedCornerImage(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 100;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;

	}

	public static Bitmap getImageOrientation(Bitmap bitmap, int rotate) {
		Bitmap bmp = null;
		try {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			if (rotate != 0) {
				Matrix mtx = new Matrix();
				mtx.setRotate(rotate);
				mtx.preRotate(rotate);
				mtx.postRotate(rotate);
				bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
				return bmp;
			} else {
				bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, null, true);
				return bmp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap scaleCenterCrop(Bitmap srcBmp) {
		if (srcBmp.getWidth() >= srcBmp.getHeight()) {

			Bitmap dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth() / 2
					- srcBmp.getHeight() / 2, 0, srcBmp.getHeight(),
					srcBmp.getHeight());
			return dstBmp;

		} else {

			Bitmap dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight()
					/ 2 - srcBmp.getWidth() / 2, srcBmp.getWidth(),
					srcBmp.getWidth());
			return dstBmp;
		}
	}

	public static Bitmap getBitmapFromPath(final String path) {
		Bitmap bm = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap
															// object
		//byte[] b = baos.toByteArray();

		return bm;
	}
	
	
	public static byte[] getByteArrayFromPath(final String path) {
		Bitmap bm = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap
															// object
		byte[] b = baos.toByteArray();

		return b;
	}
	

	public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70, stream);
		byte[] byteFormat = stream.toByteArray();
		// get the base 64 string
		String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

		return imgString;
	}

	public static File convertBitmapToFile(Context context, Bitmap mBitmap) {

		File f = new File(context.getCacheDir(), "temp");
		try {
			// create a file to write bitmap data
			f.createNewFile();

			// Convert bitmap to byte array
			Bitmap bitmap = mBitmap;

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
			byte[] bitmapdata = bos.toByteArray();

			// write the bytes in file
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f;

	}

}
