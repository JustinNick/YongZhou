package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.bean.RefundResultBean;
import com.citylinkdata.yongzhou.bean.RehargeRecordBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.utils.TimeUtil;
import com.citylinkdata.yongzhou.view.impl.iview.IRefundsView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by liqing on 2017/11/14.
 */

public class RefundsPresenter extends BasePresenter<IRefundsView>{

    public void loadRecods(String page) {
//        String url = "http://ccltest.citylinkdata.com:8008/app/oneCard-mobile/getChargeRecordList.action?reqCode=1007&appId=59806301&mobileNo=13311562144&type=03&page=1";
//        httpManager.asyncHttpGet(url, new JsonCallBack() {
//            @Override
//            public void Success(String content) {
//
//                RehargeRecordBean rechareRecord = new Gson().fromJson(content,RehargeRecordBean.class);
//                getView().onLoadSuccess(rechareRecord);
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//                getView().showToast(errorMsg);
//            }
//
//            @Override
//            public void onComplete() {
//                getView().onComplete();
//            }
//        });

        Map<String, String> par = new HashMap<>();
        par.put("reqCode", "1007");
        par.put("token", "");
        par.put("mobileNo", SPUtils.getCache(getView().getContext(), SPString.USERNAME));
        par.put("appId", SPString.APPID);
        par.put("type", "01");
        par.put("page", page);

        httpManager.asyncHttpGet(Conts.GET_CHARGE_RECORD_LIST, par, new JsonCallBack() {

            @Override
            public void Success(String content) {
                RehargeRecordBean rechareRecord = new Gson().fromJson(content, RehargeRecordBean.class);
                List<RehargeRecordBean.RecordeListBean> currentList= new ArrayList<RehargeRecordBean.RecordeListBean>();
                for (RehargeRecordBean.RecordeListBean item:rechareRecord.getRecodeList()) {
                    if(item.getStatus()!=0){
                        currentList.add(item);
                    }
                }
                rechareRecord.setRecodeList(currentList);
                if(getView()!=null)
                    getView().onLoadSuccess(rechareRecord);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if(getView()!=null)
                    getView().showToast(errorMsg);
            }

            @Override
            public void onComplete() {
                if(getView()!=null)
                    getView().onComplete();
            }
        });
    }

    public void requestRefund(String serial,String appSerial, String payorder, double chargeCount,final int position) {
        getView().showLoading(LoadString.LOADING);
        HashMap<String,String> par = new HashMap<>();
        par.put("reqCode","1026");
        par.put("mobileNo",SPUtils.getCache(getView().getContext(),SPString.USERNAME));
        par.put("appId",SPString.APPID);
        par.put("refundFee",String.valueOf((int)chargeCount));
        par.put("serial",serial);
        par.put("payOrder",payorder);
        String randomNum= (int)(Math.random()*1000)+"";
        par.put("sequenceId", TimeUtil.getCurrentTime("yyyyMMddHHmmss")+randomNum);
        //par.put("sequenceId", appSerial);
        L.e(new Gson().toJson(par));

        httpManager.asyncHttpPost(Conts.GET_CHARGE_REFUNDS, par, new JsonCallBack() {
            @Override
            public void Success(String content) {
                L.e("requestRefund=="+content);
                RefundResultBean resultBean = new Gson().fromJson(content,RefundResultBean.class);
                if(resultBean.getRespStatus()==0){
                    getView().refresh(position);
                }
                getView().showToast(resultBean.getRespMsg());

            }

            @Override
            public void onReqFailed(String errorMsg) {
                    getView().showToast(errorMsg);
            }

            @Override
            public void onComplete() {
                getView().closeLoading();
            }
        });
    }
}
