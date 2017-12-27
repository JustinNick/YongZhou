package com.citylink.tsm.citycard.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

public class ByteUtils {

	public static final String	X509		= "X.509";
	private static final char[]	HEX_DIGITS	= new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * Convert byteArray to HexString, HexString contail ":"
	 * 
	 * @param b
	 * @return
	 */
	public static String getShowHex(byte[] b) {
		if (b == null) return "";// null;
		StringBuffer sb = new StringBuffer(b.length * 3);
		for (int i = 0; i < b.length; ++i) {
			if (i > 0) sb.append(':');
			int iv = (b[i]) & 0xff;
			appendHexByte(sb, iv);
		}
		return sb.toString();
	}

	/**
	 * Convert byteArray to HexString
	 * 
	 * @param b
	 * @return
	 */
	public static String getHex(byte[] b) {
		if (b == null) return "8800";//null;
		StringBuffer sb = new StringBuffer(b.length * 3);
		for (int i = 0; i < b.length; ++i) {
			int iv = (b[i]) & 0xff;
			appendHexByte(sb, iv);
		}
		return sb.toString();
	}

	public static String getHex(byte b) {
		StringBuffer sb = new StringBuffer();
		appendHexByte(sb, b);
		return sb.toString();
	}
	
	public static String decodeUCS2(String src) {
		byte[] bytes = new byte[src.length() / 2];

		for (int i = 0; i < src.length(); i += 2) {
			bytes[i / 2] = (byte) (Integer
					.parseInt(src.substring(i, i + 2), 16));
		}
		String reValue;
		try {
			reValue = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return reValue;
	}

	public static void appendHexByte(StringBuffer sb, int b) {
		sb.append(HEX_DIGITS[(b >> 4) & 0x0f]);
		sb.append(HEX_DIGITS[b & 0x0f]);
	}

	public static byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (!TextUtils.isEmpty(hex)) {
			if (hex.length() % 2 != 0) { throw new IllegalArgumentException(); }
			char[] arr = hex.toCharArray();
			byte[] b = new byte[hex.length() / 2];
			for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
				String swap = "" + arr[i++] + arr[i];
				int byteint = Integer.parseInt(swap, 16) & 0xFF;
				b[j] = new Integer(byteint).byteValue();
			}
			return b;
		}

		return null;
	}

	public static int bytesToInt(byte[] bytes) {
		int addr = 0;
		if (bytes.length == 1) {
			addr = bytes[0] & 0xFF;
		} else {
			addr = bytes[0] & 0xFF;
			addr |= ((bytes[1] << 8) & 0xFF00);
			addr |= ((bytes[2] << 16) & 0xFF0000);
			addr |= ((bytes[3] << 24) & 0xFF000000);
		}
		return addr;
	}
	
	public static int LittleEndian(String hex) {
		// 深圳通
		// 例子:D117CE27->{0x27,0xCE,0x17,0xD1}->27CE17D1转10进制数
		int result = 0;
		byte[] data = hex2byte(hex);
		result = bytesToInt(data);
		return result;
	}
}
