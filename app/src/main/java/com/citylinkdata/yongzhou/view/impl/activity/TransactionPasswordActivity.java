package com.citylinkdata.yongzhou.view.impl.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.presenter.ResetPasswordPresenter;
import com.citylinkdata.yongzhou.userview.TimeButton;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.FormatJudge;
import com.citylinkdata.yongzhou.utils.SMSUtils;
import com.citylinkdata.yongzhou.view.impl.activity.cardRecharge.RechargeActivity;

/**
 * 交易密码
 */
public class TransactionPasswordActivity extends BaseAppActivity<ResetPasswordPresenter> implements TextWatcher {

    private EditText mEtPhone,mEtCode,mEtPassword;
    private Button mBtnModify;
    private TimeButton mBtnSendCode;


    @Override
    protected ResetPasswordPresenter getPresenter() {
        return new ResetPasswordPresenter();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_password;
    }

    @Override
    protected void initView() {

        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtnSendCode = (TimeButton) findViewById(R.id.btn_send_code);
        mBtnModify = (Button) findViewById(R.id.btn_modify);

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(getPhone())){
                    mBtnSendCode.setEnabled(false);
                }else{
                    if(getSendButtonText().contains("发送")) {
                        mBtnSendCode.setEnabled(true);
                    }
                }
            }
        });
        mEtCode.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);

        mBtnSendCode.setTextAfter("s").setTextBefore("发送").setLenght(60 * 1000);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_modify:
                presenter.setLoginPassword(getPhone(),getPassword(),getCode(),"P");
                break;
            case R.id.btn_send_code:
                if(!FormatJudge.isMobileNO(getPhone())){
                    showToast(R.string.phone_is_error);
                    mBtnSendCode.stopTime();
                    return;
                }
                SMSUtils.sendSms(this, getPhone(), "2", new SMSUtils.SMSCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFaile(String errorMsg) {
                        mBtnSendCode.stopTime();
                    }
                });
                break;
        }
    }

    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }
    public String getCode() {
        return mEtCode.getText().toString().trim();
    }
    public String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkButtonEnable();
    }

    @Override
    public void onSuccess() {
        super.onSuccess();
        showToast(R.string.modify_password_sucess);
        setResult(RechargeActivity.modifyTrdPwdRequestCode);
        AppManager.getAppManager().finishActivity();
    }


    private void checkButtonEnable(){


        if(!TextUtils.isEmpty(getPhone())&&!TextUtils.isEmpty(getCode())&&!TextUtils.isEmpty(getPassword())){
            mBtnModify.setEnabled(true);
        }else{
            mBtnModify.setEnabled(true);
        }
    }

    public String getSendButtonText() {
        return mBtnSendCode.getText().toString().trim();
    }
}
