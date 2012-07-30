package com.myideaway.android.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 * 登录数据验证类
 * 
 * 
 * 
 * </p>
 */
public class ValidationUtil {

	/**
	 * 英文字符验证
	 * 
	 * @param c
	 *            字符
	 * @return 是英文字符<code>true</code>
	 */
	private static boolean isAlpha(char c) {
		return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
	}

	/**
	 * 大写英文字符验证
	 * 
	 * @param c
	 *            字符
	 * @return 是大写英文字符 <code>true</code>
	 */
	private static boolean isCapAlpha(char c) {
		return (c >= 'A' && c <= 'Z');
	}

	/**
	 * 数字验证
	 * 
	 * @param c
	 *            字符
	 * @return 是数字 <code>true</code>
	 */
	private static boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}

	/**
	 * <code>java.util.Date</code>指定格式变换
	 * 
	 * @param format
	 *            格式
	 * @param currentTime
	 *            要求变换的时间
	 * 
	 * 
	 * 
	 * @return 变换后时间
	 * 
	 * 
	 * 
	 */
	private static String dateToString(String format, Date currentTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.getCalendar().setLenient(false);
		sdf.setLenient(false);
		return sdf.format(currentTime);
	}

	/**
	 * <p>
	 * 必须输入验证
	 * </p>
	 * 
	 * @param fieldname
	 *            filed
	 * @param value
	 *            filed
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return 指定filed输入的情况<code>true</code>
	 */
	public static boolean validateRequired(String value) {
		if (value == null || value.equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 英文字符串的验证
	 * 
	 * @param fieldname
	 *            filed�?
	 * @param value
	 *            filed�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return 英文字符�?<code>true</code>
	 */
	public static boolean validateAlpha(String value) {
		if (value == null || value.equals("")) {
			return true;
		}
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!(isAlpha(chars[i]))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 金钱的验�?
	 * </p>
	 * 
	 * @param fieldname
	 *            filed�?
	 * @param value
	 *            filed�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return �?<code>true</code>
	 */
	public static boolean validateMoneyContent(String value) {
		if (value == null || value.equals("")) {
			return true;
		}

		if (value.indexOf("'") != -1 || value.indexOf("<") != -1
				|| (value.indexOf("&") != -1 && checkHtml(value))) {
			return false;
		}

		char[] chars = value.toCharArray();
		if (chars.length > 16) {
			return false;
		}
		if (chars[0] == '0' || chars[0] == '０') {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 数字字符串的验证
	 * </p>
	 * 
	 * @param fieldname
	 *            filed�?
	 * @param value
	 *            filed�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return 数字字符�? <code>true</code>
	 */
	public static boolean validateDigit(String value) {
		if (value == null || value.equals("")) {
			return true;
		}

		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!(isDigit(chars[i]))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 为数字字符串或英文字符串
	 * </p>
	 * 
	 * @param fieldname
	 *            filed�?
	 * @param value
	 *            filed�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return �? <code>true</code>
	 */
	public static boolean validateAlphaNumeric(String value) {
		if (value == null || value.equals("")) {
			return true;
		}

		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!(isAlpha(chars[i]) || isDigit(chars[i]))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 指定数�?�范围的验证�?
	 * </p>
	 * 
	 * @param fieldname
	 *            filed�?
	 * @param min
	 *            filed�?
	 * @param max
	 *            文字列長(bytes)
	 * @param eqMin
	 *            数�?�下�?
	 * @param eqMax
	 *            数�?�上�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return 正确 <code>true</code>
	 */
	public static boolean validateNumberRange(String value, double min,
			double max, boolean eqMin, boolean eqMax) {
		double inputValue;
		double minValue;
		double maxValue;

		try {

			minValue = (new Double(min)).floatValue();
			maxValue = (new Double(max)).floatValue();

			Double tempDouble = new Double(value);
			inputValue = tempDouble.floatValue();
		} catch (Exception e) {
			return false;
		}

		if (eqMin) {
			if (inputValue < minValue) {
				return false;
			}
		} else {
			if (inputValue <= minValue) {
				return false;
			}
		}
		if (eqMax) {
			if (inputValue > maxValue) {
				return false;
			}
		} else {
			if (inputValue > maxValue) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 正确日期的验证�??
	 * </p>
	 * 
	 * @param fieldname
	 *            filed�?
	 * @param value
	 *            filed�?
	 * @param format
	 *            格式
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return 存在 <code>true</code>
	 */
	public static boolean validateDate(String value, String format) {
		if (value == null || value.equals("")) {
			return false;
		}
		Date date = null;
		String dateStr = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			date = formatter.parse(value);
			dateStr = dateToString(format, date);

		} catch (ParseException e) {
			return false;
		}
		if (date == null || !dateStr.equals(value)) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 邮件地址的验证�??
	 * </p>
	 * 
	 * @param fieldName
	 *            field�?
	 * @param value
	 *            field�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return 格式正确 <code>true</code>
	 */
	public static boolean validateEMail(String value) {
		if (value == null || value.equals("")) {
			return true;
		}

		String mailCheckString = value;
		char[] mailStringArray = mailCheckString.toCharArray();
		StringBuffer mailStringChar = new StringBuffer();
		String alllChar = "._-@";
		int count = 0;
		for (int n = 0; n < mailStringArray.length; n++) {
			if (alllChar.indexOf(mailStringArray[n]) < 0) {
				mailStringChar.append(mailStringArray[n]);
			}
		}
		mailCheckString = mailStringChar.toString();
		mailCheckString = value;
		if (mailCheckString.indexOf("..") != -1
				|| mailCheckString.indexOf("@") == -1
				|| mailCheckString.indexOf("@") == 0
				|| mailCheckString.indexOf("@") == (mailCheckString.length() - 1)
				|| mailCheckString.indexOf("@.") != -1
				|| mailCheckString.indexOf(".@") != -1
				|| mailCheckString.indexOf("_") == 0
				|| mailCheckString.indexOf("-") == 0
				|| mailCheckString.indexOf(".") == 0
				|| mailCheckString.indexOf(".") == (mailCheckString.length() - 1)
				|| mailCheckString.indexOf("-") == (mailCheckString.length() - 1)
				|| mailCheckString.indexOf("_") == (mailCheckString.length() - 1)) {
			return false;
		}
		for (int i = 0; i < mailCheckString.length(); i++) {
			if (mailCheckString.charAt(i) == '@') {
				count++;
				if (count > 1) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 日期的验�?
	 * 
	 * @param year
	 *            �?
	 * @param month
	 *            �?
	 * @param day
	 *            �?
	 * @return true OK false ERR
	 */
	public static boolean checkDate(String year, String month, String day)
			throws Exception {
		boolean ret = true;
		Calendar cal = null;
		int yy = 0;
		int mm = 0;
		int dd = 0;
		try {
			ret = checkNumber(year + month + day);
			if (ret) {
				yy = Integer.parseInt(year);
				mm = Integer.parseInt(month);
				dd = Integer.parseInt(day);
				cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
				cal.clear();
				cal.set(yy, mm - 1, dd);
				if ((yy == cal.get(cal.YEAR))
						&& (mm == (cal.get(cal.MONTH) + 1))
						&& (dd == cal.get(cal.DATE))) {
					ret = true;
				} else {
					ret = false;
				}
			}
		} catch (NumberFormatException e) {
			ret = false;
		} catch (Exception e) {
			throw e;
		} finally {
		}

		return ret;
	}

	/**
	 * 数字字符串的验证
	 * 
	 * @param str
	 *            字符�?
	 * @return true OK false ERR
	 */
	public static boolean checkNumber(String str) throws Exception {
		boolean ret = true;
		long work = 0;
		int pos = -1;

		try {
			if (1 == checkCharacterKind(str)) {
				work = Long.parseLong(str);
				pos = str.indexOf(".");
				if ((0 > work) || (pos >= 0)) {
					ret = false;
				}
			} else {
				ret = false;
			}
		} catch (NumberFormatException e) {
			ret = false;
		} catch (Exception e) {
			throw e;
		} finally {
		}

		return ret;
	}

	/**
	 * 文字种别的验�?
	 * 
	 * @param str
	 *            字符�?
	 * @return 0:半角全角混在 1:只有半角 2:只有全角
	 */
	public static int checkCharacterKind(String str) throws Exception {
		int ret = 0;
		int len = 0;
		int lenB = 0;
		try {
			len = str.length();
			byte[] work = str.getBytes();
			lenB = work.length;
			if (len == lenB) {
				ret = 1;
			} else if ((len * 2) == lenB) {
				ret = 2;
			}
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}

	public static boolean checkHtml(String strValue) {
		String strValue2 = "";
		String strTag = "";
		boolean state = false;
		int iLen;
		if (strValue.indexOf("&#") != -1) {
			state = true;
			return state;
		}
		strTag = strValue.replaceAll("&", "");
		iLen = strValue.length() - strTag.length();
		iLen += 1;
		for (int i = 0; i < iLen; i++) {
			if (strValue.indexOf("&") != -1 && strValue.indexOf(";") != -1
					&& strValue.indexOf("&") < strValue.indexOf(";")) {
				strValue2 = strValue.substring(strValue.indexOf("&") + 1,
						strValue.indexOf(";"));
				strValue = strValue.substring(strValue.indexOf("&") + 1);
				if (!strValue2.equals("") && checkEsuu(strValue2)) {
					state = true;
				}
			} else if (strValue.indexOf("&") > strValue.indexOf(";")) {
				strValue = strValue.substring(strValue.indexOf("&"));
			}
		}
		return state;
	}

	private static boolean isAlph(char c) {
		return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
	}

	public static boolean checkEsuu(String strValue) {
		boolean state = true;
		char[] chars = strValue.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!(isAlph(chars[i]))) {
				return false;
			}
		}
		return state;
	}

	/**
	 * @param fieldname
	 *            フィールド名
	 * @param value
	 *            フィールド入力�??
	 * @param length
	 *            文字列長
	 * @param eq
	 *            文字列長チェックが以内チェックであるか同�?チェックであるか
	 * @param err_id
	 *            エラーメッセージID
	 * @param errors
	 *            エラー情報リスト
	 * @return 文字列長が正しければ <code>true</code>
	 */
	public static boolean validateStrLen(String value, int length, boolean eq) {
		if (value.indexOf("'") != -1 || value.indexOf("<") != -1) {// ||
																	// value.indexOf("%")!=-1
																	// ||value.indexOf("_")!=-1)
																	// {
			return false;
		}
		if (eq) {
			if (value.length() != length) {
				return false;
			}
		} else {
			if (value.length() > length) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param fieldname
	 *            フィールド名
	 * @param value
	 *            フィールド入力�??
	 * @param length
	 *            文字列長
	 * @param eq
	 *            文字列長チェックが以内チェックであるか同�?チェックであるか
	 * @param err_id
	 *            エラーメッセージID
	 * @param errors
	 *            エラー情報リスト
	 * @return 文字列長が正しければ <code>true</code>
	 */
	public static boolean validateStrLen1(String value, int length, boolean eq) {
		if (value.indexOf("<") != -1) {// || value.indexOf("%")!=-1
										// ||value.indexOf("_")!=-1) {
			return false;
		}
		if (eq) {
			if (value.length() != length) {
				return false;
			}
		} else {
			if (value.length() > length) {
				return false;
			}
		}
		return true;
	}

	private static final int NOCHECK = -1;

	/**
	 * <p>
	 * 指定的输入框的数值格式和位数的验证。
	 * 
	 * 
	 * </p>
	 * 
	 * @param fieldname
	 *            filed名
	 * 
	 * 
	 * @param value
	 *            输入值
	 * 
	 * 
	 * @param precision
	 *            总位数
	 * 
	 * 
	 * @param scale
	 *            小数点后位数
	 * @param eq
	 *            位数是否相等或者以内 true 相等
	 * @param err_id
	 *            messageID
	 * @param errors
	 *            错误对象
	 * @return 格式正确并且位数正确 <code>true</code>
	 */
	public static boolean validateNumber(String value, int precision,
			int scale, boolean eq) {
		if (value == null || value.equals("")) {
			return true; // 入力なしは正当とみなす

		}
		BigDecimal dec;
		try {
			dec = new BigDecimal(value);
		} catch (NumberFormatException e) {
			return false;
		}
		if (precision != NOCHECK) {
			String decString = dec.toString();
			int decPrecision = decString.length();
			if (decString.startsWith("+") || decString.startsWith("-")) {
				decPrecision--;
			}
			if (decString.indexOf('.') >= 0) {
				decPrecision--;
			}
			if (eq) {
				if (decPrecision != precision) {
					return false;
				}
			} else {
				if (decPrecision > precision) {
					return false;
				}
			}
		}
		if (scale != NOCHECK) {
			if (eq) {
				if (dec.scale() != scale) {
					return false;
				}
			} else {
				if (dec.scale() > scale) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 英文字符串的验证
	 * 
	 * @param fieldname
	 *            filed�?
	 * @param value
	 *            filed�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return 英文字符�?<code>true</code>
	 */
	public static boolean validateEscape(String value) {
		if (value == null || value.equals("")) {
			return true;
		}
		for (int i = 0; i < value.length(); i++) {
			if (!(isNotEscape(value.substring(i, i + 1)))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @param c
	 * @return <code>true</code>
	 */
	private static boolean isNotEscape(String c) {
		for (int i = 0; i < ESCAPE_CHARACTOR.length; i++) {
			if (c.equals(ESCAPE_CHARACTOR[i])) {
				return false;
			}
		}
		return true;
	}

	private static final String[] ESCAPE_CHARACTOR = { "%", "_" };

	/**
	 * <p>
	 * 学员批量报名金钱的验证
	 * </p>
	 * 
	 * @param value
	 *            filed�?
	 * @param err_id
	 *            错误编号ID
	 * @param errors
	 *            错误信息对象
	 * @return �?<code>true</code>
	 */
	public static boolean validateMoneyContent(String value, String errorStr) {
		if (value == null || value.equals("")) {
			return true;
		}

		if (value.indexOf("'") != -1 || value.indexOf("<") != -1
				|| (value.indexOf("&") != -1 && checkHtml(value))) {
			errorStr = "不是数字";
			return false;
		}

		char[] chars = value.toCharArray();
		if (chars.length > 7) {
			errorStr = "大于7位";
			return false;
		}
		return true;
	}
}
