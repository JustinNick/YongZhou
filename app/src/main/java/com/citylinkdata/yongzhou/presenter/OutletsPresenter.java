package com.citylinkdata.yongzhou.presenter;

import com.citylinkdata.yongzhou.bean.OutletsBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.view.impl.iview.IBaseView;
import com.citylinkdata.yongzhou.view.impl.iview.IOutletsView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/29.
 */

public class OutletsPresenter extends BasePresenter<IOutletsView> {
    public void loadOutletsData() {

        httpManager.asyncHttpPost(Conts.GET_OUTLETS, null, OutletsBean.class, new ReqCallBack<OutletsBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(OutletsBean result) {

                getView().onLoadOutletsSuccess(result.getData());
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }
}
