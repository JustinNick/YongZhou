package com.citylinkdata.yongzhou.view.impl.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.presenter.LoginPresenter;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.ILoginView;
import com.umeng.analytics.MobclickAgent;

public class GuideActivity extends BaseAppActivity {
    public static int TOMAIN = 0;
    public static int TOLOGIN = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

           if(msg.what==0){
               //检测用户是否已经登录过，如有保存用户名，则认为是用户的登录行为，使用友盟统计进行埋点统计
               if(TextUtils.isEmpty(SPString.USERNAME)) {
                   MobclickAgent.onProfileSignIn(SPUtils.getCache(GuideActivity.this, SPString.USERNAME));
             }
               startActivity(new Intent(GuideActivity.this,MainActivity.class));
           }else{
               startActivity(new Intent(GuideActivity.this,LoginActivity.class));
           }


            finish();
            return false;
        }
    });

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        hideActionBar();
        mHandler.sendEmptyMessageDelayed(TOMAIN,1000);


    }

}
