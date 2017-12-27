package com.citylinkdata.yongzhou.view.impl.activity;

import android.webkit.WebView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.view.impl.activity.BaseWebActivity;

/**
 * Created by hbob on 2017/11/10.
 */

public class BusLineStationQueryActivity extends BaseWebActivity {
    @Override
    public String getWebTitle() {
        return getResources().getString(R.string.bus_line_query);
    }

    @Override
    public boolean isShowloading() {
        return false;
    }

    @Override
    public String getUrl() {
        return Conts.BUS_LINE_STATION_QUERY;
    }

    @Override
    protected void initView() {
        super.initView();
        showLoading("加载中...");
    }

    @Override
    public void onWebFinished(WebView view, String url) {
        closeLoading();
    }
}
