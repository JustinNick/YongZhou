package com.citylinkdata.yongzhou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class CardTabAdapter extends FragmentPagerAdapter {
    private Fragment[] list_fragment;                         //fragment列表
    private String[] list_Title;                              //tab名的列表

    public CardTabAdapter(FragmentManager fm, Fragment[] list_fragment, String[] list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment[position];
    }

    @Override
    public int getCount() {
        return list_Title.length;
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title[position % list_Title.length];
    }
}
