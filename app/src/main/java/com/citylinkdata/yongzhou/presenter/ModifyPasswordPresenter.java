package com.citylinkdata.yongzhou.presenter;

import android.text.TextUtils;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.LoadString;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.FormatJudge;
import com.citylinkdata.yongzhou.utils.MD5;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IBaseView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqing on 2017/11/3.
 */

public class ModifyPasswordPresenter extends BasePresenter<IBaseView>{


    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param newComfirmPassword 新密码
     */
    public void modifyPassword(String oldPassword, String newPassword, final String newComfirmPassword) {
        if(TextUtils.isEmpty(oldPassword)){
            getView().showToast(R.string.old_password_can_not_be_empty);
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            getView().showToast(R.string.new_password_can_not_be_empty);
            return;
        }
        if(TextUtils.isEmpty(newComfirmPassword)){
            getView().showToast(R.string.comfirm_password_can_not_be_empty);
            return;
        }

        if(!newPassword.equals(newComfirmPassword)){
            getView().showToast(R.string.confirm_the_password_is_inconsistent);
            return;
        }
        if(oldPassword.equals(newPassword)){
            getView().showToast(R.string.new_password_is_the_same_as_the_old_password);
            return;
        }
        if(!FormatJudge.isPasswordLetterAndNum(newPassword)){
            getView().showToast(R.string.password_is_error);
            return;
        }

        getView().showLoading(LoadString.LOADING);
        String username = SPUtils.getCache(getView().getContext(), SPString.USERNAME);
        Map<String,String> par = new HashMap<>();
        par.put("mobileNo",username);
        par.put("loginPwd", MD5.getMD5String(oldPassword));
        par.put("NewloginPwd", MD5.getMD5String(newComfirmPassword));

        httpManager.asyncHttpPost(Conts.MODIFY_PASSWORD, par, null, new ReqCallBack() {
            @Override
            public void onComplete() {
                getView().closeLoading();
            }

            @Override
            public void onReqSuccess(Object result) {
                getView().onSuccess();
                SPUtils.setCache(getView().getContext(), SPString.PASSWORD,MD5.getMD5String(newComfirmPassword));
            }

            @Override
            public void onReqFailed(String errorMsg) {
                getView().showToast(errorMsg);
            }
        });
    }
}
