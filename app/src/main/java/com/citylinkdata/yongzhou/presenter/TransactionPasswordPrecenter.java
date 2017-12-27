package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SMSUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/8.
 */

public class TransactionPasswordPrecenter extends BasePresenter {

    public void setTransactionPassword(final String phone, final String password, String code){
        getView().showLoading(LoadString.LOADING);

        // 1 验证支付密码

        SMSUtils.checkSms(getView().getContext(),phone,"2",code,new SMSUtils.SMSCallback(){

            @Override
            public void onSuccess() {
                updateTrPassword(phone,password);
            }

            @Override
            public void onFaile(String errorMsg) {

            }
        });

    }

    private void updateTrPassword(String phone,String password){
        Map<String,String> par = new HashMap<>();
        par.put("reqCode","1004");
        par.put("userName",phone);
        par.put("appId", SPString.APPID);
        par.put("newLoginPwd",password);
        par.put("mobileNo",phone);
            httpManager.asyncHttpPost(Conts.MODIFY_TR_PWD, par, new JsonCallBack() {
                @Override
                public void Success(String content) {
                    L.e("TRPWE==="+content);
                }

                @Override
                public void onReqFailed(String errorMsg) {

                }

                @Override
                public void onComplete() {

                }
            });
    }
}
