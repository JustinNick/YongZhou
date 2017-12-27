package com.citylinkdata.yongzhou.presenter;

import android.content.Context;
import android.util.Log;

import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.MD5;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IRechargeView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/8.
 */

public class RechargePresenter extends BasePresenter<IRechargeView> {

//    public void getH5(){
//        Map<String,String> par = new HashMap<>();
//        Context context = getView().getContext();
//        par.put("username", SPUtils.getCache(context, SPString.USERNAME));
//        par.put("password", SPUtils.getCache(context, SPString.PASSWORD));
////        par.put("appVersion","release");
//        par.put("appVersion","debug");
//        par.put("appId",SPString.APPID);
//        par.put("alipayapp","Y");
//        par.put("type","1");
//        par.put("mobileNo",SPUtils.getCache(context, SPString.USERNAME));
//
//        httpManager.asyncHttpPost(Conts.GET_RECHARGE_H5, par, new JsonCallBack() {
//            @Override
//            public void Success(String content) {
//                L.i(content);
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//    }

    public void fillUp(){

    }
}
