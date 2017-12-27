package com.citylinkdata.bus;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTableLayout;
    private String[] mStrs;
    private Fragment mOrdinaryFragment,mTransferFragment ;
    private Fragment[] mFragments;
    private CardTabAdapter mCardTabAdapter;
    private ViewPager mViewPager;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_activity_main);



        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTableLayout = (TabLayout) findViewById(R.id.tablayout);

        mOrdinaryFragment = new OrdinaryFragment();
        mTransferFragment = new TransferFragment();

        mStrs = new String[]{"站点查询","换乘查询"};
        mFragments = new Fragment[]{mOrdinaryFragment, mTransferFragment};

        //设置TabLayout的模式
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        int size = mFragments.length;
        for(int i=0;i<size;i++){
            mTableLayout.addTab(mTableLayout.newTab().setText(mStrs[i]));
        }

        mCardTabAdapter = new CardTabAdapter(getSupportFragmentManager(),mFragments,mStrs);

        //viewpager加载adapter
        mViewPager.setAdapter(mCardTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        mTableLayout.setupWithViewPager(mViewPager);

    }

    public void onClick(View v){
        hintKbTwo();
        finish();
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //极光统计 页面的统计

        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //极光统计 页面的统计

        MobclickAgent.onPause(this);
    }
}
