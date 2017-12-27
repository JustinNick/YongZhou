package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.ReRegistBean;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.utils.FormatJudge;
import com.citylinkdata.yongzhou.utils.MD5;
import com.citylinkdata.yongzhou.utils.SMSUtils;
import com.citylinkdata.yongzhou.utils.SystemUtil;
import com.citylinkdata.yongzhou.view.impl.iview.IRegisterView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class RegisterPresenter extends BasePresenter<IRegisterView> {

    private IRegisterView view = getView();



    public void register(final String username, final String password, String code){
        if(!FormatJudge.isMobileNO(username)){
            getView().showToast(R.string.phone_is_error);
            getView().closeLoading();
            return;
        }

        if(!FormatJudge.isPasswordLetterAndNum(password)){
            getView().showToast(R.string.password_is_error);
            getView().closeLoading();
            return;
        }
        if(!FormatJudge.isNumerCode(code,6)){
            getView().showToast(R.string.vertifycode_is_error);
            getView().closeLoading();
            return;
        }
        getView().showLoading("正在注册");
        SMSUtils.checkSms(getView().getContext(), username, "0", code, new SMSUtils.SMSCallback() {
            @Override
            public void onSuccess() {
                registerUser(username,password);
            }

            @Override
            public void onFaile(String errorMsg) {
                getView().closeLoading();
            }
        });
    }

    public void registerUser(String username, String password){


        Map<String,String> par = new HashMap<>();
        par.put("mobileNo",username);
        par.put("loginPwd", MD5.getMD5String(password));
        par.put("mobileType", SystemUtil.getSystemModel());
        par.put("AppVersion","Android");
        getView().showLoading("正在注册");
        httpManager.asyncHttpPost(Conts.REGIST, par, ReRegistBean.class, new ReqCallBack<ReRegistBean>() {
            @Override
            public void onComplete() {
                getView().closeLoading();
            }

            @Override
            public void onReqSuccess(ReRegistBean result) {
                getView().showToast(R.string.register_success);
                AppManager.getAppManager().finishActivity();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                getView().showToast(errorMsg);
            }
        });
    }


}
