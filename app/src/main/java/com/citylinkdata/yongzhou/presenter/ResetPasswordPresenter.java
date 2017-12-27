package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.FormatJudge;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.MD5;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/8.
 */

public class ResetPasswordPresenter extends BasePresenter {

    /**
     *  重置登录密码和交易密码接口
     * @param phone  电话
     * @param password 密码
     * @param code  验证码
     * @param PasswordForType  类型 L=登录密码，P=支付密码 大写
     */
    public void setLoginPassword(String phone, String password, String code,String PasswordForType) {

        if(!FormatJudge.isMobileNO(phone)){
            getView().showToast(R.string.phone_is_error);
            getView().closeLoading();
            return;
        }

        if(PasswordForType.contains("L")&&!FormatJudge.isPasswordLetterAndNum(password)){
            getView().showToast(R.string.password_is_error);
            getView().closeLoading();
            return;
        }

        if(PasswordForType.contains("P")&&!FormatJudge.isNumerCode(password,6)){
            getView().showToast(R.string.tr_password_is_error);
            getView().closeLoading();
            return;
        }

        if(!FormatJudge.isNumerCode(code,6)){
            getView().showToast(R.string.vertifycode_is_error);
            getView().closeLoading();
            return;
        }
        getView().showLoading(LoadString.LOADING);
        Map<String,String> par = new HashMap<>();
        par.put("Mobile",phone);
        par.put("VerifyCode",code);
        par.put("NewPassword", MD5.getMD5String(password).toUpperCase());
        par.put("PasswordForType",PasswordForType);
        L.e(new Gson().toJson(par));
        httpManager.asyncHttpPost(Conts.RESET_PASSWORD, par, null, new ReqCallBack<String>() {
            @Override
            public void onComplete() {
                getView().closeLoading();
            }

            @Override
            public void onReqSuccess(String result) {
                L.i(result);
                getView().onSuccess();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                getView().showToast(errorMsg);
            }
        });



    }
}
