package com.citylinkdata.yongzhou.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.UiHelp;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.presenter.ModifyPasswordPresenter;
import com.citylinkdata.yongzhou.utils.AppManager;

public class ModifyPasswordActivity extends BaseAppActivity<ModifyPasswordPresenter> {

    private EditText mEtOldPwd,mEtNewPwd,mEtNewComfirmPwd;

    @Override
    protected ModifyPasswordPresenter getPresenter() {
        return new ModifyPasswordPresenter();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initView() {

        mEtOldPwd = (EditText) findViewById(R.id.et_old_password);
        mEtNewPwd = (EditText) findViewById(R.id.et_new_password);
        mEtNewComfirmPwd = (EditText) findViewById(R.id.et_new_password_confirm);
    }

    public void onClick(View v){

        presenter.modifyPassword(getOldPassword(),getNewPassword(),getNewComfirmPassword());
    }

    @Override
    public void onSuccess() {
        super.onSuccess();
        showToast(R.string.modify_password_sucess);
        AppManager.getAppManager().finishActivity(MainActivity.class);
        UiHelp.jumpLogin(this);
        AppManager.getAppManager().finishActivity();
    }

    private String getOldPassword(){
        return mEtOldPwd.getText().toString().trim();
    }
    private String getNewPassword(){
        return mEtNewPwd.getText().toString().trim();
    }
    private String getNewComfirmPassword(){
        return mEtNewComfirmPwd.getText().toString().trim();
    }
}
