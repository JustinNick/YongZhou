package com.citylinkdata.yongzhou.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.RegisterUserBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.http.JsonCallBack;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IBaseView;
import com.citylinkdata.yongzhou.view.impl.iview.IPersonInfomationView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by dell on 2017/11/21.
 */

public class PersonInformationPresenter extends BasePresenter<IPersonInfomationView> {
    public void updateInfomation(String nickname,String realname,String gender,String idcode,String avaterPicUrl){
        if(TextUtils.isEmpty(nickname)){
            getView().showToast(R.string.updateinfo_no_nickname);
            return;
        }
        if(TextUtils.isEmpty(realname)){
            getView().showToast(R.string.updateinfo_no_realname);
            return;
        }
        if(TextUtils.isEmpty(gender)){
            getView().showToast(R.string.updateinfo_no_gender);
            return;
        }
        if(TextUtils.isEmpty(idcode)){
            getView().showToast(R.string.updateinfo_no_idcode);
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("ID", SPUtils.getCache(getView().getContext(),SPString.USER_ID));
        map.put("Nickname",nickname);
        map.put("Realname",realname);
        map.put("IDCode",idcode);
        map.put("Gender",gender);
        map.put("AvatarPictureUrl",avaterPicUrl);
        L.e(new Gson().toJson(map));
        httpManager.asyncHttpPost(Conts.UPDATE_PERSON_INFORMATION, map, RegisterUserBean.class, new ReqCallBack<RegisterUserBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onReqSuccess(RegisterUserBean result) {
                if(result.isSuccess()){
                    getView().onSaveed();
                }else{
                    getView().onSaveFiled(result.getMessage());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                getView().onSaveFiled(errorMsg);
            }
        });
    }

}
