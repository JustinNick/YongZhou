package com.citylinkdata.yongzhou.utils;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;

import static com.citylinkdata.yongzhou.R.drawable.lock;

/**
 * Created by liqing
 * on 2016/11/4.
 * Email:liqingqq50@qq.com
 */
public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;
	private static Object object=new Object();
	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈 
	 */
	public void addActivity(Activity activity) {

			if (activityStack == null) {
				activityStack = new Stack<Activity>();
			}
			activityStack.add(activity);

	}

	/**
	 *  获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {

		if (activityStack != null && activityStack.size() > 0) {
			Activity activity = activityStack.lastElement();
			finishActivity(activity);
		}

	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public synchronized void  finishActivity(Class<?> cls) {
		if(getActivityByName(cls)==null){
			return;
		}

		try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        }catch (ConcurrentModificationException e){

        }


	}

	/**
	 * 获取指定类名的Activity
	 */
	public Activity getActivityByName(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				return activity;
			}
		}
		return null;
	}
	
	
	/**
	 * 结束所有Activity 
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 获取顶部的activity的name
	 * 
	 * @return
	 */
	public String getTopActivityName(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		if (runningTaskInfos != null)
			return (runningTaskInfos.get(0).topActivity).toString();
		else
			return null;
	}
	
	public Activity getTopActivity() {
		if (activityStack != null && activityStack.size() > 0){
			return activityStack.lastElement();
		}
		else {
			return null;
		}
			
	}

	public int getActivityCount() {
		int size = activityStack == null ? 0 : activityStack.size();
		return size;
	}

	/**
	 * 退出应用程序
	 */
	@SuppressLint("NewApi")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {	
			
		}
	}


	/**
	 * 判断某个服务是否正在运行的方法
	 *
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

}