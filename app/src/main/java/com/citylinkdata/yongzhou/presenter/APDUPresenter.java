package com.citylinkdata.yongzhou.presenter;

import android.widget.Toast;

import com.citylinkdata.yongzhou.bean.APDUBean;
import com.citylinkdata.yongzhou.bean.ReAPDUBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.L;
import com.google.gson.Gson;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqing on 2017/11/9.
 */

public class APDUPresenter {
    HttpManager httpManager;
    private OnAPDUPresenter onAPDUPresenter;

    public void setOnAPDUPresenter(OnAPDUPresenter onAPDUPresenter) {
        this.onAPDUPresenter = onAPDUPresenter;
    }

    public void getAPDU(final APDUBean apduBean) {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        Map<String, String> par = new HashMap<>();
        par.put("reqCode", apduBean.getReqCode());
        par.put("sequenceId", apduBean.getSequenceId());
        par.put("loginUser", apduBean.getLoginUser());
        par.put("mobileNo", apduBean.getMobileNo());
        par.put("cardNo", apduBean.getCardNo());
        par.put("money", apduBean.getMoney());
        par.put("appId", apduBean.getAppId());
        par.put("chargePwd", apduBean.getChargePwd());
        par.put("payChannel", apduBean.getPayChannel());
        par.put("tn", apduBean.getTn());
        par.put("cardBalance", apduBean.getCardBalance());
        par.put("lastRecord", apduBean.getLastRecord());
        par.put("meid", apduBean.getMeid());
        par.put("currentAppAid", apduBean.getCurrentAppAid());
        par.put("mobileType", apduBean.getMobileType());
        par.put("apduSum", apduBean.getApduSum());
        par.put("lastApduSW", apduBean.getLastApduSW());
        par.put("lastData", apduBean.getLastData());
        par.put("lastApdu", apduBean.getLastApdu());
        par.put("sigNature", apduBean.getSigNature());
        par.put("token", apduBean.getToken());
        par.put("encParam",  apduBean.getChargePwd());
        String sendData=new Gson().toJson(par);
        L.e("APDU请求数据：" + sendData);
        httpManager.asyncHttpPost(Conts.APDU, par, new JsonCallBack() {
            @Override
            public void Success(String content) {
                L.e("APDU返回数据："+content);

                ReAPDUBean reAPDUBean = new Gson().fromJson(content, ReAPDUBean.class);
//                onAPDUPresenter.onReChargeConinue(reAPDUBean,content);
                //如果是可疑记录验证处理，则都走成功接口，不用判断返回状态
                if(apduBean.getReqCode().equals("1010")){
                    onAPDUPresenter.onReChargeSuccess(reAPDUBean);
                    return;
                }

                switch (reAPDUBean.getRespStatus()){
                    case 0:
                        onAPDUPresenter.onReChargeSuccess(reAPDUBean);
                        break;

                    default:
                        onAPDUPresenter.onReChargeFailed(reAPDUBean.getRespMsg());
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }

            @Override
            public void onComplete() {

            }

        });

    }

    public interface OnAPDUPresenter {
        void onReChargeSuccess(ReAPDUBean reAPDUBean);

        void onReChargeFailed(String respmsg);

//        void onReChargeConinue(ReAPDUBean apdulist,String content);


    }
}
