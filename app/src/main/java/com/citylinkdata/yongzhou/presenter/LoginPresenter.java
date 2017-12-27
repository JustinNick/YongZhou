package com.citylinkdata.yongzhou.presenter;

import android.text.TextUtils;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.ReLoginBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.MD5;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.ILoginView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/2.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {

    public void login(final String userName, final String password) {
        if(TextUtils.isEmpty(userName)){
            getView().showToast(R.string.username_can_not_be_empty);
            return;
       }

        if(TextUtils.isEmpty(password)){
            getView().showToast(R.string.password_can_not_be_empty);
            return;
        }

        if(userName.length()!=11){
            getView().showToast(R.string.no_fill_mobile_number);
            return;
        }
        if(password.length()<6){
            getView().showToast(R.string.no_fill_login_password);
            return;
        }


        Map<String,String> par = new HashMap<>();
        par.put("mobileNo",userName);
        par.put("loginPwd", MD5.getMD5String(password));
        getView().showLoading(LoadString.LOADING);
        L.e("地址："+Conts.LOGIN);
        httpManager.asyncHttpPost(Conts.LOGIN, par, ReLoginBean.class, new ReqCallBack<ReLoginBean>() {
            @Override
            public void onComplete() {
                getView().closeLoading();

            }

            @Override
            public void onReqSuccess(ReLoginBean result) {
                ReLoginBean.DataBean loginData= result.getData();
                SPUtils.setCache(getView().getContext(), SPString.USERNAME,userName);
                SPUtils.setCache(getView().getContext(), SPString.PASSWORD, MD5.getMD5String(password));
                SPUtils.setCache(getView().getContext(), SPString.USER_ID,loginData.getID());
                SPUtils.setCache(getView().getContext(), SPString.TOKEN,loginData.getToken());
                SPUtils.setCache(getView().getContext(), SPString.ICON,loginData.getAvatarPictureUrl());
                SPUtils.setCache(getView().getContext(),SPString.NICKNAME,loginData.getNickname()==null?userName:loginData.getNickname());
                SPUtils.setCache(getView().getContext(),SPString.REALNAME,loginData.getRealname());
                SPUtils.setCache(getView().getContext(),SPString.IDCODE,loginData.getIDCode());
                SPUtils.setCache(getView().getContext(),SPString.GENDER,loginData.getGender());

                getView().showToast(R.string.login_success);

                getView().onLogin();
            }

            @Override
            public void onReqFailed(String errorMsg) {

                getView().onLoginFail(errorMsg);
            }
        });
    }
}
