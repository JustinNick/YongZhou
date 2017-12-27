package com.citylinkdata.yongzhou.view.impl.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.presenter.LoginPresenter;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.ILoginView;
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends BaseAppActivity<LoginPresenter> implements ILoginView {

    private EditText mEtUserName,mEtPassword;
    private int type;

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        hideActionBar();
        mEtUserName = (EditText) findViewById(R.id.et_username);
        mEtPassword = (EditText) findViewById(R.id.et_password);

        type = getIntent().getIntExtra("type",0);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:

                presenter.login(getUserName(), getPassword());
//                presenter.login("13622546332", "li123321");

                break;
        }
    }


    public String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    public String getUserName() {
        return mEtUserName.getText().toString().trim();
    }

    /**
     * 登录成功跳转主界面
     */
    @Override
    public void onLogin() {
        //登录埋点
        MobclickAgent.onProfileSignIn(SPUtils.getCache(this, SPString.USERNAME));
        setResult(3);
        if(type==0) {
            startActivity(new Intent(this, MainActivity.class));
        }
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void onLoginFail(String errorMsg) {
        showToast(errorMsg);
    }
}
