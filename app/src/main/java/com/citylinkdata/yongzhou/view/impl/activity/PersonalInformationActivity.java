package com.citylinkdata.yongzhou.view.impl.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.SPString;
import com.citylinkdata.yongzhou.presenter.PersonInformationPresenter;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.SPUtils;
import com.citylinkdata.yongzhou.view.impl.iview.IPersonInfomationView;

public class PersonalInformationActivity extends BaseAppActivity<PersonInformationPresenter> implements IPersonInfomationView, View.OnClickListener {

    private EditText mNickname;
    private EditText mRealname;
    private RadioGroup mGender;
    private RadioButton mGender_Male;
    private RadioButton mGender_Female;
    private EditText mIDCode;
    private Context mContext;
    private Button mSaveBtn ;

    @Override
    protected PersonInformationPresenter getPresenter() {
        return new PersonInformationPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void initView() {
        mContext = getContext();
        mNickname = (EditText) findViewById(R.id.et_nickname);
        mRealname = (EditText)findViewById(R.id.et_realname);
        mGender = (RadioGroup)findViewById(R.id.radio_gender);
        mIDCode = (EditText)findViewById(R.id.et_idcode);
        mGender_Male = (RadioButton)findViewById(R.id.radio_male);
        mGender_Female = (RadioButton)findViewById(R.id.radio_female);

        mSaveBtn = (Button)findViewById(R.id.btn_modify_personinfomation);
        mSaveBtn.setOnClickListener(this);
        mNickname.setText(SPUtils.getCache(mContext,SPString.NICKNAME));
        mRealname.setText(SPUtils.getCache(mContext,SPString.REALNAME));
        mIDCode.setText(SPUtils.getCache(mContext,SPString.IDCODE));

        String gender= SPUtils.getCache(mContext,SPString.GENDER);
//        mGender.check(R.id.radio_male);
        mGender_Male.setChecked(gender.equals("男"));
        mGender_Female.setChecked(gender.equals("女"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_modify_personinfomation:
                String nickname=mNickname.getText().toString();
                String realname=mRealname.getText().toString();
                String gender = ((RadioButton)findViewById(mGender.getCheckedRadioButtonId())).getText().toString();
                String idcode= mIDCode.getText().toString();
                presenter.updateInfomation(nickname,realname,gender,idcode,null);
                break;
        }
    }


    @Override
    public void onSaveed() {
        String nickname=mNickname.getText().toString();
        String realname=mRealname.getText().toString();
        String gender = ((RadioButton)findViewById(mGender.getCheckedRadioButtonId())).getText().toString();
        String idcode= mIDCode.getText().toString();

        SPUtils.setCache(mContext,SPString.NICKNAME,nickname);
        SPUtils.setCache(mContext,SPString.REALNAME,realname);
        SPUtils.setCache(mContext,SPString.GENDER,gender);
        SPUtils.setCache(mContext,SPString.IDCODE,idcode);
        showToast("修改成功。");
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void onSaveFiled(String msg) {
        showToast(msg);
    }
}
