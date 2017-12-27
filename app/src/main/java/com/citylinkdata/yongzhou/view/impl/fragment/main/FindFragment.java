package com.citylinkdata.yongzhou.view.impl.fragment.main;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.LifeMenuAdapter;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.model.LifeItemModels;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.utils.SystemUtil;
import com.citylinkdata.yongzhou.view.impl.activity.LoginActivity;
import com.citylinkdata.yongzhou.view.impl.activity.cardRecharge.RefundsActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.BaseFragment;
import com.qibu.sdk.thirdpart.LoanThirdpart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liqing on 2017/10/26.
 * 惠生活
 */
public class FindFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private EditText etPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        listView = (ListView) contentView.findViewById(R.id.listview);
        etPhone = (EditText) contentView.findViewById(R.id.et_phone);

        //360借贷申请权限
        if(Build.VERSION.SDK_INT>=23){
            boolean request = LoanThirdpart.requestPermission(mContext);}

        //------------宿主调用初始化
        LoanThirdpart.setRes(R.anim.activity_open_enter, R.anim.activity_open_exit,
                R.anim.activity_close_enter, R.anim.activity_close_exit);
        int code = LoanThirdpart.init(mContext.getApplicationContext());
        //------------宿主调用初始化

        LifeItemModels lifeItemModels = new LifeItemModels(mContext);
        listView.setAdapter(new LifeMenuAdapter(mContext,lifeItemModels.getLifeMenuItems()));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(TextUtils.isEmpty(SPUtils.getCache(mContext,SPString.USERNAME))){
            UiHelp.verifyLoginJump(mContext,LoginActivity.class,null);
            return;
        }

        switch (position){
            case 0:
//                //给360测试用
//                int result = LoanThirdpart.startLoan(getActivity(),
//                        etPhone.getText().toString().trim(),
//                        "");
//                if (result == LoanThirdpart.START_NOT_INIT) {
//                    showToast("没有初始化");
//                } else if (result == LoanThirdpart.START_PARAM_WRONG) {
//                    showToast("入参错误");
//                }


//                ------------启动  360借贷
                String phonestr = SPUtils.getCache(mContext, SPString.USERNAME);
                if(TextUtils.isEmpty(phonestr)){
                    UiHelp.jumpLogin(mContext);
                    return;
                }
                int result = LoanThirdpart.startLoan(getActivity(),
                        phonestr,
                        "");
                if (result == LoanThirdpart.START_NOT_INIT) {
                    showToast("没有初始化");
                } else if (result == LoanThirdpart.START_PARAM_WRONG) {
                    showToast("入参错误");
                }
                break;
            case 1:
                //微商城
                UiHelp.jumpToSeaAmoy(mContext,getSeaAmoyUrl(),"",true);
                break;
            case 2:
                UiHelp.jumpToInsurance(mContext);
                break;


        }
    }


    /**
     * 海淘商城地址拼接
     * @return
     */
    private String getSeaAmoyUrl() {
        Map<String, String> par = new HashMap<>();
        par.put("channel","555555");
        par.put("os","01");
        par.put("userid","");
        //certType:01:身份证  02:护照  03:其他
        par.put("certType","01");
        par.put("certNo","");
        par.put("city",SPString.CITY_CODE);
        par.put("version", SystemUtil.getVersionName(mContext));//系统版本
        par.put("userName", SPUtils.getCache(mContext,SPString.NICKNAME));//系统版本
        par.put("telePhone", SPUtils.getCache(mContext,SPString.USERNAME));//系统版本

        String data = par.toString();
        String subData = data.substring(1, data.length() - 1).replace(", ", "&");
        L.e("haitaourl=="+subData);
        return Conts.GET_MICRO_MALL_URL + "?" + subData;
    }


}
