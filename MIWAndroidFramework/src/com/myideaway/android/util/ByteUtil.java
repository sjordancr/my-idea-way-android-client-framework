package com.myideaway.android.util;

import java.io.UnsupportedEncodingException;


public class ByteUtil {
	public static byte[] getBytes(byte[] data, int offset, int len) {
		byte[] tmp = new byte[len];
		for (int i = 0; i < len; i++) {
			tmp[i] = data[offset + i];
		}

		return tmp;
	}

	public static String bytes2String(byte[] data) {
		String result = null;
		int availableLen = data.length;
		
		for(int i = 0; i < availableLen; i++){
			if(data[i] == 0){
				availableLen = i;
				break;
			}
		}
		
		try {
			if (availableLen == 1) {
				result = String.valueOf((int) data[0]);
			} else {
				result = new String(data, 0, availableLen, "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {
			return null;
		}

		return result;
	}

	public static int bytes2Int(byte[] data) {

		if (data.length == 1) {
			return (int) data[0];
		}
		else {
			int mask = 0xff;
			int temp = 0;
			int n = 0;
			for (int i = 0; i < 4; i++) {
				n <<= 8;
				temp = data[i] & mask;
				n |= temp;
			}
			return n;
		}
	}
	
	public static String connectBytesAsString(byte[] data, String mark){
		StringBuffer strBuf = new StringBuffer();
		
		int dataLen = data.length;
		for(int i = 0; i < dataLen; i++){
			strBuf.append(data[i]);
			
			if(i < dataLen - 1){
				strBuf.append(mark);
			}
		}
		
		return strBuf.toString();
	}
}
