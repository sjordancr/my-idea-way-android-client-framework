package com.myideaway.android.util;

import android.util.Log;

public class LogUtil {
	private static String tag;

	public static void debug(String msg) {
		String appendedMsg = appendMsgAndInfo(msg, getCurrentInfo());
		Log.d(tag, appendedMsg);
	}

	public static void error(String msg, Throwable tr) {
		String appendedMsg = appendMsgAndInfo(msg, getCurrentInfo());
		Log.e(tag, appendedMsg, tr);
	}

	public static void info(String msg) {
		String appendedMsg = appendMsgAndInfo(msg, getCurrentInfo());
		Log.i(tag, appendedMsg);
	}

	private static String getCurrentInfo() {

		StackTraceElement[] eles = Thread.currentThread().getStackTrace();
		StackTraceElement targetEle = eles[5];
		String info = "(" + targetEle.getClassName() + "."
				+ targetEle.getMethodName() + ":" + targetEle.getLineNumber()
				+ ")";
		return info;
	}

	private static String appendMsgAndInfo(String msg, String info) {
		return msg + " " + getCurrentInfo();
	}

	public static String getTag() {
		return tag;
	}

	public static void setTag(String tag) {
		LogUtil.tag = tag;
	}

}
