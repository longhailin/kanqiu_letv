package com.letv.watchball.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.letv.watchball.LetvApplication;

public class NetWorkTypeUtils {

	public static final int NETTYPE_NO = 0;
	public static final int NETTYPE_WIFI = 1;
	public static final int NETTYPE_2G = 2;
	public static final int NETTYPE_3G = 3;

	public static boolean isNetAvailable() {
		return NetWorkTypeUtils.getAvailableNetWorkInfo() == null;
	}

	public static boolean isThirdGeneration() {
		TelephonyManager telephonyManager = (TelephonyManager) LetvApplication
				.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		int netWorkType = telephonyManager.getNetworkType();
		switch (netWorkType) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_EDGE:

			return false;
		default:
			return true;
		}
	}

	public static boolean isWifi() {

		NetworkInfo networkInfo = getAvailableNetWorkInfo();

		if (networkInfo != null) {

			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			}

		}

		return false;
	}

	public static NetworkInfo getAvailableNetWorkInfo() {
		NetworkInfo activeNetInfo = null;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) LetvApplication
					.getInstance().getSystemService(
							Context.CONNECTIVITY_SERVICE);
			activeNetInfo = connectivityManager.getActiveNetworkInfo();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (activeNetInfo != null && activeNetInfo.isAvailable()) {
			return activeNetInfo;
		} else {
			return null;
		}
	}

	public static String getNetWorkType() {

		String netWorkType = "";
		NetworkInfo netWorkInfo = getAvailableNetWorkInfo();

		if (netWorkInfo != null) {
			if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				netWorkType = "1";
			} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

				TelephonyManager telephonyManager = (TelephonyManager) LetvApplication
						.getInstance().getSystemService(
								Context.TELEPHONY_SERVICE);

				switch (telephonyManager.getNetworkType()) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
					netWorkType = "2";
					break;
				case TelephonyManager.NETWORK_TYPE_EDGE:
					netWorkType = "3";
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
					netWorkType = "4";
					break;
				// case TelephonyManager.NETWORK_TYPE_HSDPA:
				// netWorkType = "5";
				// break;
				// case TelephonyManager.NETWORK_TYPE_HSUPA:
				// netWorkType = "6";
				// break;
				// case TelephonyManager.NETWORK_TYPE_HSPA:
				// netWorkType = "7";
				// break;
				case TelephonyManager.NETWORK_TYPE_CDMA:
					netWorkType = "8";
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					netWorkType = "9";
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					netWorkType = "10";
					break;
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					netWorkType = "11";
				default:
					netWorkType = "-1";
				}

			}

		}
		return netWorkType;
	}

	public static int getNetType() {

		ConnectivityManager connectivityManager = (ConnectivityManager) LetvApplication
				.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isAvailable()) {
			if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
				return NETTYPE_WIFI;
			} else {
				TelephonyManager telephonyManager = (TelephonyManager) LetvApplication
						.getInstance().getSystemService(
								Context.TELEPHONY_SERVICE);

				switch (telephonyManager.getNetworkType()) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_EDGE:
					return NETTYPE_2G;
				default:
					return NETTYPE_3G;
				}
			}
		} else {
			return NETTYPE_NO;
		}
	}
}
