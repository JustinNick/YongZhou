package com.citylinkdata.yongzhou.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatJudge {

	/**
	 * 判断电话号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobilePass(String mobiles) {
		Pattern p = Pattern
		.compile("[a-zA-Z0-9]{8,16}");
		Matcher m = p.matcher(mobiles);

		return m.matches();
		}
	/**
	 * 判断手机电话号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		//为匹配现市面大部份手机
		Pattern p = Pattern
				.compile("^((1[3-9][0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		//以前的手机匹配规则
//		Pattern p = Pattern
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//		Matcher m = p.matcher(mobiles);
//
		return m.matches();
	}

	/**
	 * 判断6-20位字母+数字
	 * @param password
	 * @return
	 */
	public static boolean isPasswordLetterAndNum(String password){
		Pattern p = Pattern
				.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
		Matcher m = p.matcher(password);

		return m.matches();
	}

	/**
	 * 判断邮箱格式
	 * @param email
	 * @return
	 */
	
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
		}
	/**
	 * 判断是否全是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
		return false;
		}
		return true;
		}
	/**
	 * 判断n位数字
	 * @param str
	 * @return
	 */
	public static boolean isNumerCode(String str,int n) {
		Pattern pattern = Pattern.compile("^\\d{"+n+"}$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
