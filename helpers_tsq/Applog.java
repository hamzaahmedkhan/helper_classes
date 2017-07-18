package com.app.helpers;

import com.ingic.garagediscount.constants.AppConstants;

import android.util.Log;


public class Applog {
	
	

	public static void Debug(final String tag, final String msg){
		
		if(Utils.isEmptyOrNull(msg))
		return;
		
		if(AppConstants.DEBUG)
		Log.d(tag, msg);
	}

	public static void Error(final String tag, final String msg) {
		
		if(Utils.isEmptyOrNull(msg))
			return;
		
		if(AppConstants.DEBUG)
		Log.e(tag, msg);
	} 
	
	


}
