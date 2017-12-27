package com.citylinkdata.bus.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.citylinkdata.bus.CitycodeConfig;
import com.citylinkdata.bus.R;
import com.citylinkdata.bus.adapter.TipsAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class SearchPointActivity extends AppCompatActivity implements Inputtips.InputtipsListener {

    private EditText mEtSearch;
    private TipsAdapter tipsAdapter;
    List<Tip> tips = new ArrayList<>();
    private ListView listview;
    private int type;
    private String searchText;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            tipsAdapter.notifyDataSetChanged();
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_point);


        type = getIntent().getIntExtra("type",0);
        searchText = getIntent().getStringExtra("searchtext");

        mEtSearch = (EditText) findViewById(R.id.et_input);
        listview = (ListView) findViewById(R.id.listview_search);
        initTipsAdapter();
        if(type==0){
            mEtSearch.setHint(getResources().getString(R.string.start_input_search));
        }else{
            mEtSearch.setHint(getResources().getString(R.string.end_input_search));
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("tip",tips.get(i));
                setResult(0,intent);
                finish();
            }
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(mEtSearch.getText().toString().trim());
            }
        });


        if(!TextUtils.isEmpty(searchText)){
            mEtSearch.setText(searchText);
            mEtSearch.setSelection(mEtSearch.getText().toString().trim().length());
        }
    }


    private void search(String s) {
        InputtipsQuery inputquery = new InputtipsQuery(s, CitycodeConfig.CITYCODE);

        inputquery.setCityLimit(true);//限制在当前城市
        Inputtips inputTips = new Inputtips(this, inputquery);

        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    private void initTipsAdapter() {
        if(tipsAdapter==null){
            tipsAdapter = new TipsAdapter(this,tips);
            listview.setAdapter(tipsAdapter);
        }else {
            tipsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        tips.clear();
        tips.addAll(list);
        handler.sendEmptyMessage(0);
    }

    public void onClick(View v){
        finish();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getRunningActivityName());
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getRunningActivityName());
        MobclickAgent.onPause(this);
    }

    private String getRunningActivityName() {
        String contextString =this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

}
