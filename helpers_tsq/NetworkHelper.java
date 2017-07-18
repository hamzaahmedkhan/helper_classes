package com.app.helpers;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkHelper {


	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if(manager == null)
		return false;
		
		// 3g-4g available
		boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		// wifi available
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();

		System.out.println(is3g + " net " + isWifi);

		if (!is3g && !isWifi) {
			return false;
		} else
			return true;

	}

	



}
