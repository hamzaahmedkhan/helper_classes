package com.app.helpers;


import android.content.Context;
import android.graphics.Color;

import com.app.transformations.TransformationType;
import com.squareup.picasso.Transformation;

import com.app.transformations.ColorFilterTransformation;
import com.app.transformations.CropCircleTransformation;
import com.app.transformations.CropSquareTransformation;
import com.app.transformations.CropTransformation;
import com.app.transformations.RoundedCornersTransformation;

public class TransformationHelper {

	

	public static Transformation getTransformation(TransformationType typeOfTransformation,
			final Context context) {
		Transformation transformation = null;
		switch (typeOfTransformation) {
		case CropTop:
			transformation = new CropTransformation(300, 100,
					CropTransformation.CropType.TOP);
			break;
		case CropCenter:
			transformation = new CropTransformation(300, 100);
			break;
		case CropBottom:
			transformation = new CropTransformation(300, 100,
					CropTransformation.CropType.BOTTOM);
			break;
		case CropSquare:
			transformation = new CropSquareTransformation();
			break;
		case CropCircle:
			transformation = new CropCircleTransformation();
			break;
		case ColorFilter:
			transformation = new ColorFilterTransformation(Color.argb(80, 255,
					0, 0));
			break;

		case RoundedCorners:
			transformation = new RoundedCornersTransformation(10, 0);
			break;

		}
		return transformation;

	}
	
	
	
	public static Transformation getTransformation(TransformationType typeOfTransformation,
			final Context context,final int radius) {
		Transformation transformation = null;
		switch (typeOfTransformation) {
		case CropTop:
			transformation = new CropTransformation(300, 100,
					CropTransformation.CropType.TOP);
			break;
		case CropCenter:
			transformation = new CropTransformation(300, 100);
			break;
		case CropBottom:
			transformation = new CropTransformation(300, 100,
					CropTransformation.CropType.BOTTOM);
			break;
		case CropSquare:
			transformation = new CropSquareTransformation();
			break;
		case CropCircle:
			transformation = new CropCircleTransformation();
			break;
		case ColorFilter:
			transformation = new ColorFilterTransformation(Color.argb(80, 255,
					0, 0));
			break;
		case RoundedCorners:
			transformation = new RoundedCornersTransformation(radius, 1);
			break;
		}
		return transformation;

	}

}
