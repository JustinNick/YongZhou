package com.citylink.tsm.citycard.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class ZLogCard {

	private  static final int printLevel = ZLogCard.PRINT_LEVEL_DEBUG;
	private  static final String TAG = "citylinkdata.tsm : ";

	public static final int PRINT_LEVEL_VERBOSE = 0;
	public static final int PRINT_LEVEL_DEBUG = 1;
	public static final int PRINT_LEVEL_INFO = 2;
	public static final int PRINT_LEVEL_WARN = 3;
	public static final int PRINT_LEVEL_ERROR = 4;

	/**
	 * ZLog采用单例，但是有一点需要注意，TAG需要不停的刷新，要不都是前一个页面的TAG
	 * 在onResume中是个不错选择，但是有些特殊情况像后台请求，对话框，Fragment这些记得都刷新Tag
	 *
	 * @return
	 */

	private static String generateTag(){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[4];
		String tag = "%s.%s(L:%d)";
		String className = stack.getClassName();
		className = className.substring(className.lastIndexOf(".")+1);
		tag = String.format(tag, stack.getClassName(),className,stack.getLineNumber());
		tag = TAG==null?tag:" "+tag;
		return tag;
	}



	public static void i(String key, String value) {

		String msg = key + ":" + value;
		if (TextUtils.isEmpty(value)) {
			Log.e(generateTag(), msg);
		} else {
			Log.i(generateTag(), msg);
		}
	}

	public static void i(String msg) {

		Log.i(generateTag(), msg);
	}



	public static void d(String msg) {

		Log.d(generateTag(), msg);

	}


	public static void w(String msg) {

		Log.w(generateTag(), msg);
	}


	public static void e(String msg) {

		Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (printLevel > PRINT_LEVEL_VERBOSE) {
			return;
		}
		Log.v(generateTag(), msg);
	}



	public static void printMap(HashMap<String, Object> map) {
		i("--------------printMap-begin----------------");
		Set<String> keySet = map.keySet();
		for (String str : keySet) {
			i("key:" + str + " value:" + map.get(str));
		}
		i("--------------printMap-end----------------");
	}

	private static File getLogFile() {
		Calendar calendar = Calendar.getInstance();
		File folder = new File(Environment.getExternalStorageDirectory(),
				"TsmLogs/" + (calendar.get(Calendar.MONTH) + 1) + "-"
						+ calendar.get(Calendar.DAY_OF_MONTH));
		if (!folder.exists())
			folder.mkdirs();

		String fileName = android.os.Build.MODEL + "ctcc"+calendar.get(Calendar.HOUR_OF_DAY) + ".log";
		File file = new File(folder.getAbsolutePath(), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return file;
	}

	private static void writeLogtoFile(ArrayList<String> logList) {
		if (logList == null || logList.size() == 0)
			return;
		File logFile = getLogFile();
		try {
			FileWriter filerWriter = new FileWriter(logFile, true);
			BufferedWriter bufWriter = new BufferedWriter(filerWriter);
			// 写头
			bufWriter.newLine();
			bufWriter.newLine();

			for (String log : logList) {
				bufWriter.write(log);
				bufWriter.newLine();
			}
			bufWriter.close();
			filerWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeLog() {
		i("writeLog");
		try {
			ArrayList<String> cmdLine = new ArrayList<String>(); // 设置命令logcat-d读取日志
			cmdLine.add("logcat");
			cmdLine.add("-d");
			switch (printLevel) {
			case PRINT_LEVEL_VERBOSE: {
				cmdLine.add("*:V");
				break;
			}
			case PRINT_LEVEL_DEBUG: {
				cmdLine.add("*:D");
				break;
			}
			case PRINT_LEVEL_INFO: {
				cmdLine.add("*:I");
				break;
			}
			case PRINT_LEVEL_ERROR: {
				cmdLine.add("*:E");
				break;
			}
			case PRINT_LEVEL_WARN: {
				cmdLine.add("*:W");
				break;
			}
			}
			ArrayList<String> clearLog = new ArrayList<String>(); // 设置命令logcat-c清除日志
			clearLog.add("logcat");
			clearLog.add("-c");
			Process process = Runtime.getRuntime().exec(
					cmdLine.toArray(new String[cmdLine.size()])); // 捕获日志
			// Process process = Runtime.getRuntime().exec("logcat -d");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream())); // 将捕获内容转换为BufferedReader

			// Runtime.runFinalizersOnExit(true);
			String str = null;
			int index = 0;
			ArrayList<String> logList = new ArrayList<String>();
			while ((str = bufferedReader.readLine()) != null) {
				if (index++ % 100 == 0 && index != 0)
					Runtime.getRuntime().exec(
							clearLog.toArray(new String[clearLog.size()]));
				// 清理日志....这里至关重要，不清理的话，任何操作都将产生新的日志，代码进入死循环，直到bufferreader满
				logList.add(str);
			}
			writeLogtoFile(logList);
		} catch (Exception e) {
			e.printStackTrace();
			i("writeLog Exception ");
		}
	}
}
