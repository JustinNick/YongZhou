package com.citylink.tsm.citycard.utils;

import android.text.TextUtils;

public class FormatUtils {
	public static String formatAmount(String amountResp) {
		try {
			if (TextUtils.isEmpty(amountResp)) return "获取失败";
			long amountValue = Long.parseLong(amountResp, 16);
			int yuan = (int) (amountValue / 100);
			int jiao = (int) ((amountValue % 100) / 10);
			int fen = (int) (amountValue % 10);
			return yuan + "." + jiao + fen + "元";
			//return String.valueOf(amountValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "获取失败";
	}

	//用于深圳通充值和可疑验证传参balance进制的修改
	public static String formatAmountSZT(String amountResp) {
		try {
			if (TextUtils.isEmpty(amountResp)) {
				return "获取失败";
			}
			long amountValue = Long.parseLong(amountResp, 16);
			return String.valueOf(amountValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "获取失败";
	}

	public static String formatTime(String time) {
		try {
			String h = time.substring(0, 2);
			String m = time.substring(2, 4);
			String s = time.substring(4, 6);
			return h + ":" + m + ":" + s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String formatAccountBalance(String money) {
		try {
			if (TextUtils.isEmpty(money)) return "获取失败";
			long value = Long.parseLong(money);
			int y = (int) (value / 100);
			int j = (int) ((value % 100) / 10);
			int f = (int) (value % 100);
			return y + "." + j + f + "元";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "获取失败";
	}

	public static String formatDate(String date) {
		try {
			String y = date.substring(0, 4);
			String m = date.substring(4, 6);
			String d = date.substring(6, 8);
			return y + "-" + m + "-" + d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
