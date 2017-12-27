package com.citylinkdata.yongzhou.utils;

import android.content.Context;
import android.widget.Toast;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.SendSMSBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.http.ReqCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/7.
 */

public class SMSUtils {



    /**
     * 发送短信
     * @param phone 手机号码
     * @param type 短信类型 0-注册，1-重置登录密码，2-重置支付密码
     */
    public static void sendSms(final Context context, String phone, String type, final SMSCallback callback){
      HttpManager httpManager = new HttpManager();
        Map<String,String> par = new HashMap<>();
        par.put("MobileNumber",phone);
        par.put("SmsType",type);
        httpManager.asyncHttpPost(Conts.SEND_SMS,par,SendSMSBean.class,new ReqCallBack<SendSMSBean>(){

            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(SendSMSBean result) {
                if(result.isSuccess()) {
                    Toast.makeText(context, context.getResources().getString(R.string.send_sms_success), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,result.getMessage(),Toast.LENGTH_SHORT).show();
                    callback.onFaile(result.getMessage());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();
                callback.onFaile(errorMsg);
            }
        });
    }

    /**
     * 发送短信
     * @param phone 手机号码
     * @param type 短信类型 0-注册，1-重置登录密码，2-重置支付密码
     */
    public static void checkSms(final Context context, String phone, String type, String VerifyCode, final SMSCallback callback){
        HttpManager httpManager = new HttpManager();
        Map<String,String> par = new HashMap<>();
        par.put("MobileNumber",phone);
        par.put("SmsType",type);
        par.put("VerifyCode",VerifyCode);
        httpManager.asyncHttpPost(Conts.CHECK_SMS,par,null,new ReqCallBack(){

            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(Object result) {
                callback.onSuccess();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                callback.onFaile(errorMsg);
                Toast.makeText(context,errorMsg,Toast.LENGTH_SHORT).show();

            }
        });
    }


   public interface SMSCallback{
        void onSuccess();
        void onFaile(String errorMsg);
    }

}
