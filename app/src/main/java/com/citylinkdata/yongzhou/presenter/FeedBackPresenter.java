package com.citylinkdata.yongzhou.presenter;

import android.text.TextUtils;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.utils.SystemUtil;
import com.citylinkdata.yongzhou.view.impl.iview.IFeedBackView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/2.
 */

public class FeedBackPresenter extends BasePresenter<IFeedBackView> {


    public void updateFeed(String content, String phone,String fbType) {
        if(TextUtils.isEmpty(content)){
            getView().showToast(R.string.feedback_content_can_not_be_empty);
            return;
        }
        if(TextUtils.isEmpty(phone)){
            getView().showToast(R.string.please_enter_a_valid_phone_number);
            return;
        }
        if(TextUtils.isEmpty(fbType)){
            getView().showToast(R.string.feedback_type_unselected);
            return;
        }
        getView().showLoading(LoadString.FEEDBACK_UPDATE);

        Map<String,String> par = new HashMap<>();
        par.put("PhoneType", SystemUtil.getSystemModel());
        par.put("QType",fbType);
//        par.put("LinkMan", "");
        par.put("Msg", content);
        par.put("MobileNO",phone);
        par.put("RegisterUserID", SPUtils.getCache(getView().getContext(), SPString.USER_ID));
        httpManager.asyncHttpPost(Conts.FEEDBACK_ADD, par, null, new ReqCallBack() {
            @Override
            public void onComplete() {
                getView().closeLoading();
            }

            @Override
            public void onReqSuccess(Object result) {
                getView().showToast(R.string.update_success);
                getView().onSuccess();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                getView().showToast(errorMsg);
            }
        });

    }
}
