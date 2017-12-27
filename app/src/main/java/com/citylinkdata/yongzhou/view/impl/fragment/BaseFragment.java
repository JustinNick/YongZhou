package com.citylinkdata.yongzhou.view.impl.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.citylinkdata.yongzhou.userview.LoadingDialog;
import com.citylinkdata.yongzhou.view.impl.activity.BaseAppActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Dell on 2017/10/26.
 */

public abstract class BaseFragment extends Fragment {
    protected View contentView;
    protected Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        contentView =inflater.inflate(getLayoutId(),null);

        if(getActivity()!=null) {
            mContext = getActivity();
        }


        initView();


        return contentView;

    }
    /**
     * 这个页面的布局
     * @return id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化加载栏
     */
    protected abstract void initView();



    protected void showLoading(String msg){
        if(mContext!=null) {
            ((BaseAppActivity) mContext).showLoading(msg);
        }
    }


    protected void closeLoading(){
        ((BaseAppActivity)mContext).closeLoading();
    }

    protected void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public void showToast(int strId) {
        Toast.makeText(mContext, mContext.getResources().getString(strId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }


}
