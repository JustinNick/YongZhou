package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.bean.RehargeRecordBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.iview.IRechargeRecordView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/14.
 */

public class RechargeRecordPresenter extends BasePresenter<IRechargeRecordView> {


    public void loadRecods(String page) {
        Map<String, String> par = new HashMap<>();
        par.put("reqCode", "1007");
        par.put("token", "");
        par.put("mobileNo", SPUtils.getCache(getView().getContext(), SPString.USERNAME));
        par.put("appId", SPString.APPID);
        par.put("type", "3");
        par.put("page", page);

        httpManager.asyncHttpGet(Conts.GET_CHARGE_RECORD_LIST, par, new JsonCallBack() {

            @Override
            public void Success(String content) {
                RehargeRecordBean rechareRecord = new Gson().fromJson(content, RehargeRecordBean.class);
                if(getView()!=null)
                    getView().onLoadSuccess(rechareRecord);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                getView().showToast(errorMsg);
            }

            @Override
            public void onComplete() {
                if(getView()!=null)
                    getView().onComplete();
            }
        });
    }
}
