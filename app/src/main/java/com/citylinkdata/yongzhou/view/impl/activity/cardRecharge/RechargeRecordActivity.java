package com.citylinkdata.yongzhou.view.impl.activity.cardRecharge;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.adapter.TabAdapter;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.citylinkdata.yongzhou.view.impl.fragment.rechargerecord.FillUpRecordFragment;
import com.citylinkdata.yongzhou.view.impl.fragment.rechargerecord.RechargeRecordFragment;

/**
 * 充值记录
 * nfc，通卡宝，补登充值
 */
public class RechargeRecordActivity extends BaseAppActivity {

    private TabLayout mTableLayout;
    private String[] mStrs;
    private Fragment mRechargeRecordFragment,mFillUpRecordFragment ;
    private Fragment[] mFragments;
    private TabAdapter mTabAdapter;
    private ViewPager mViewPager;
    private Context ctx;


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(com.citylinkdata.bus.R.id.viewpager);
        mTableLayout = (TabLayout) findViewById(com.citylinkdata.bus.R.id.tablayout);

        mRechargeRecordFragment = new RechargeRecordFragment();
        mFillUpRecordFragment = new FillUpRecordFragment();

        mStrs = new String[]{"充值记录","补登记录"};
        mFragments = new Fragment[]{mRechargeRecordFragment, mFillUpRecordFragment};

        //设置TabLayout的模式
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        int size = mFragments.length;
        for(int i=0;i<size;i++){
            mTableLayout.addTab(mTableLayout.newTab().setText(mStrs[i]));
        }

        mTabAdapter = new TabAdapter(getSupportFragmentManager(),mFragments,mStrs);

        //viewpager加载adapter
        mViewPager.setAdapter(mTabAdapter);
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

}
