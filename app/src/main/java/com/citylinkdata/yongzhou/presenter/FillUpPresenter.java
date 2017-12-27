package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.bean.CheckCardBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IFillUpView;
import com.citylinkdata.yongzhou.utils.L;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/12/12.
 */

public class FillUpPresenter extends BasePresenter<IFillUpView> {

    public void checkCard(String cardNum) {
        Map<String, String> par = new HashMap<>();
        par.put("mobileNo", SPUtils.getCache(getView().getContext(),SPString.USERNAME));
        par.put("cardNum",cardNum);

        httpManager.asyncHttpPost(Conts.CHECK_CARD, par, CheckCardBean.class, new ReqCallBack<CheckCardBean>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(CheckCardBean result) {
                getView().onSuccess(result);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                getView().showToast(errorMsg);
            }
        });

    }
}
