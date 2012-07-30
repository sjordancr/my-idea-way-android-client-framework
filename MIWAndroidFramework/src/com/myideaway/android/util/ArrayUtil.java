package com.myideaway.android.util;

public class ArrayUtil {

	public static long[] convert(Object[] objs) {
		long[] array = new long[0];

		if (objs == null) {
			return array;
		} else {
			array = new long[objs.length];
		}

		for (int i = 0; i < objs.length; i++) {
			array[i] = Long.parseLong(objs[i].toString());
		}

		return array;
	}
}
