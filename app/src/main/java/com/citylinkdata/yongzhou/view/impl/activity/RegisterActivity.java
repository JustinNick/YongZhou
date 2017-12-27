package com.citylinkdata.yongzhou.view.impl.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.presenter.RegisterPresenter;
import com.citylinkdata.yongzhou.userview.TimeButton;
import com.citylinkdata.yongzhou.utils.FormatJudge;
import com.citylinkdata.yongzhou.utils.SMSUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IRegisterView;

public class RegisterActivity extends BaseAppActivity<RegisterPresenter> implements IRegisterView, TextWatcher {

    private EditText mEtPhone;
    private EditText mEtPassword;
    private EditText mEtcode;
    private CheckBox mCbAgree;
    private Button mBtnRegister;
    private TimeButton mBtnSendCode;


    @Override
    protected RegisterPresenter getPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        presenter = new RegisterPresenter();
        presenter.attach(this);

        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtcode = (EditText) findViewById(R.id.et_code);
        mCbAgree = (CheckBox) findViewById(R.id.cb_agree);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnSendCode = (TimeButton) findViewById(R.id.btn_send_code);

        String mSPhone = mEtPhone.getText().toString().trim();
        String mSPassword = mEtPassword.getText().toString().trim();

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(getUserName())) {
                    mBtnSendCode.setEnabled(false);
                } else {
                    if(getSendButtonText().contains("发送")) {
                        mBtnSendCode.setEnabled(true);
                    }
                }
            }
        });
        mEtPassword.addTextChangedListener(this);
        mEtcode.addTextChangedListener(this);

        mBtnSendCode.setTextAfter("s").setTextBefore("发送").setLenght(60 * 1000);

    }

    /**
     * 当手机号和密码不为空时，设置注册按纽为可点击
     */
    private void setButtonEnable() {

        if (!TextUtils.isEmpty(getUserName()) && !TextUtils.isEmpty(getPassword())) {
            mBtnRegister.setEnabled(true);
        } else {
            mBtnRegister.setEnabled(false);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (mCbAgree.isChecked()) {
                    presenter.register(getUserName(), getPassword(), getCode());
                } else {
                    showToast(R.string.register_tips);
                }
                break;
            case R.id.tv_registration_agreement:
                //用户注册协议
                Intent intent=new Intent(this,BaseStandardWebActivity.class);
                intent.putExtra("title",getResources().getString(R.string.user_Registration_Agreement));
                intent.putExtra("url", Conts.APP_SYS_INFO + "?p=agreement");
                startActivity(intent);
                break;
            case R.id.btn_send_code:
                if(!FormatJudge.isMobileNO(getUserName())){
                    showToast(R.string.phone_is_error);
                    mBtnSendCode.stopTime();
                    return;
                }
                //发送验证码
                SMSUtils.sendSms(this, getUserName(), "0", new SMSUtils.SMSCallback() {
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

    private void register() {
        HttpManager httpManager = new HttpManager(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setButtonEnable();
    }

    private String getUserName() {
        return mEtPhone.getText().toString().trim();
    }

    private String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    private String getCode() {
        return mEtcode.getText().toString().trim();
    }

    public String getSendButtonText() {
        return mBtnSendCode.getText().toString().trim();
    }
}
