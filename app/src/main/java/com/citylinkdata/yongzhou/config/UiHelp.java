package com.citylinkdata.yongzhou.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.InsuranceActivity;
import com.citylinkdata.yongzhou.view.impl.SeaAmoyActivity;
import com.citylinkdata.yongzhou.view.impl.activity.BaseStandardWebActivity;
import com.citylinkdata.yongzhou.view.impl.activity.CardInquireActivity;
import com.citylinkdata.yongzhou.view.impl.activity.LoginActivity;
import com.citylinkdata.yongzhou.view.impl.activity.TransactionPasswordActivity;
import com.citylinkdata.yongzhou.view.impl.activity.cardRecharge.RechargeActivity;
import com.citylinkdata.yongzhou.view.impl.activity.cardRecharge.RefundsActivity;

/**
 * Created by liqing on 2017/11/1.
 */

public class UiHelp {

    /**
     * 跳转时验证用户是否登陆
     * @param context
     * @param cls
     * @param bundle
     */
    public static void verifyLoginJump(Context context, Class cls, Bundle bundle){
        Intent intent = new Intent();

        if(bundle!=null){
            intent.putExtra("data",bundle);
        }

        if(TextUtils.isEmpty(SPUtils.getCache(context,SPString.USERNAME))){
            intent.setClass(context,LoginActivity.class);
            intent.putExtra("type",1);
            ((Activity)context).startActivityForResult(intent,0);
//            context.startActivity(intent);
            return;
        }

        intent.setClass(context,cls);
        context.startActivity(intent);
    }


    public static void Jump(Context context, Class cls, Bundle bundle){
        Intent intent = new Intent(context,cls);
        if(bundle!=null){
            intent.putExtra("data",bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 跳转到登录页面
     * @param context
     */
    public static void jumpLogin(Context context) {
        Intent intent = new Intent(context,LoginActivity.class);

        context.startActivity(intent);
    }




    /**
     * 跳转重置交易密码页面
     * 0;充值界面跳转，1:其它界面跳转
     * @param context
     */
    public static void jumpResetTradePassword(Context context,int type) {
        Intent intent = new Intent(context, TransactionPasswordActivity.class);
        if(type==0){
            ((Activity)context).startActivityForResult(intent,RechargeActivity.modifyTrdPwdRequestCode);
        }else {
            context.startActivity(intent);
        }
    }

    /**
     * 跳转退款界面，不验证是否登陆
     * @param context
     */
    public static void jumpRefundsNoVertify(Context context) {
        Intent intent = new Intent(context, RefundsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转退款界面，不验证是否登陆
     * @param context
     */
    public static void jumpToBusSearch(Context context) {
        Intent intent = new Intent(context, com.citylinkdata.bus.MainActivity.class);
        context.startActivity(intent);
    }

    public static void jumpNFC(Context context) {
        Intent intent =  new Intent(Settings.ACTION_NFC_SETTINGS);
        context.startActivity(intent);
    }

    public static void jumpBlueTooth(Context context) {

        Intent intent =  new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到标准网页
     * @param mContext
     * @param loadUrl  加载地址
     * @param title  导航栏标题
     */
    public static void jumpToStandardWeb(Context mContext, String loadUrl, String title,boolean isShowLoading) {
        Intent intent = new Intent(mContext, BaseStandardWebActivity.class);
        intent.putExtra("url",loadUrl);
        intent.putExtra("title",title);
        intent.putExtra("isShowLoading",isShowLoading);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到选择保险购买页面
     * @param mContext
     */
    public static void jumpToInsurance(Context mContext) {
        Intent intent = new Intent(mContext, InsuranceActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * 跳转到海淘商城
     * @param mContext
     * @param loadUrl  加载地址
     * @param title  导航栏标题
     */
    public static void jumpToSeaAmoy(Context mContext, String loadUrl, String title,boolean isShowLoading) {
        Intent intent = new Intent(mContext, SeaAmoyActivity.class);
        intent.putExtra("url",loadUrl);
        intent.putExtra("title",title);
        intent.putExtra("isShowLoading",isShowLoading);
        mContext.startActivity(intent);
    }
}
