package com.citylinkdata.yongzhou.view.impl.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.bean.DataWordBean;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.http.HttpManager;
import com.citylinkdata.yongzhou.http.ReqCallBack;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.presenter.FeedBackPresenter;
import com.citylinkdata.yongzhou.userview.TitleView;
import com.citylinkdata.yongzhou.utils.AppManager;
import com.citylinkdata.yongzhou.utils.L;
import com.citylinkdata.yongzhou.view.impl.iview.IFeedBackView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackActivity extends BaseAppActivity<FeedBackPresenter> implements TextWatcher,IFeedBackView {
    EditText mEditContent;
    EditText mEtPhone;
    TextView mTvTextCount;
    Spinner mSpFeedbackType;
    List<DataWordBean.DataWordDetailBean> mFeedbackTypeList;
    String mSelectedFeedbackTypeGuid="00000000-0000-0000-0000-000000000000";
    HttpManager mHttpManager;
    Context mContext;
    @Override
    protected FeedBackPresenter getPresenter() {
        return new FeedBackPresenter();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        mContext=getContext();
        mEditContent = (EditText) findViewById(R.id.et_content);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvTextCount = (TextView) findViewById(R.id.tv_text_count);
        mSpFeedbackType = (Spinner)findViewById(R.id.sp_feedbacktype);
        mSpFeedbackType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedFeedbackTypeGuid = mFeedbackTypeList.get(position).getID();
                L.e("选择的反馈类型ID="+mSelectedFeedbackTypeGuid);
                L.e("选择反馈类型：" + mSpFeedbackType.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mEditContent.addTextChangedListener(this);
        mHttpManager=new HttpManager(mContext);
        //加载反馈类型数据
        loadFeedbackType();
    }

    public void onClick(View v){
        presenter.updateFeed(getContent(),getPhone(),getFeedbackType());
    }


    public String getContent() {
        return mEditContent.getText().toString().trim();
    }

    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }

    public String getFeedbackType(){
        return mSelectedFeedbackTypeGuid;
    }

    @Override
    public void onSuccess() {
        AppManager.getAppManager().finishActivity();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mTvTextCount.setText(mEditContent.getText().length()+"/200");
    }

    private void loadFeedbackType(){
        Map postData=new HashMap();
        postData.put("Ident","QuestionType");
       mHttpManager.asyncHttpPost(Conts.GET_DATA_WORD, postData, DataWordBean.class, new ReqCallBack<DataWordBean>() {
           @Override
           public void onComplete() {

           }

           @Override
           public void onReqSuccess(final DataWordBean result) {
               List data_list = new ArrayList<String>();
               mFeedbackTypeList = result.getData();
                for(DataWordBean.DataWordDetailBean dw :result.getData()){
                    data_list.add(dw.getName());
                }
               //适配器
               ArrayAdapter arr_adapter= new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, data_list);
               //设置样式
               arr_adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
               //加载适配器
               mSpFeedbackType.setAdapter(arr_adapter);
           }

           @Override
           public void onReqFailed(String errorMsg) {

           }
       });

    }
}
