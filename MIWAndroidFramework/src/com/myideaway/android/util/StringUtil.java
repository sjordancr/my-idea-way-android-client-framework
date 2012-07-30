package com.myideaway.android.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isNullOrEmpty(String str) {
		boolean isAvailable = true;

		if (str != null) {
			str = str.trim();
			if (!str.equals("")) {
				isAvailable = false;
			}
		}

		return isAvailable;
	}

	public static String changeCharset(String str, String from, String to) throws UnsupportedEncodingException{
		String newStr = null;
		newStr = new String(str.getBytes(from), to);
		return newStr;
	}

	public static String[] regexCompile(String regex, String str){
		String[] strs = new String[0];
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if(m.find() && m.groupCount() > 0){
			strs = new String[m.groupCount()];
			for(int i = 0; i < m.groupCount(); i++){
				strs[i] = m.group(i + 1);
			}
		}
		
		return strs;
	}
    
    public static String parseString(Object obj) {
		return String.valueOf(obj);
	}
}
