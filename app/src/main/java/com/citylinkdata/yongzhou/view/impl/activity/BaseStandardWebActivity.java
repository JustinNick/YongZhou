package com.citylinkdata.yongzhou.view.impl.activity;

import android.webkit.WebView;

/**
 * Created by liqing on 2017/11/3.
 * 标准webview
 */

public class BaseStandardWebActivity extends BaseWebActivity{
    @Override
    public String getWebTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    public boolean isShowloading() {
        return getIntent().getBooleanExtra("isShowLoading",false);
    }

    @Override
    public String getUrl() {
        return getIntent().getStringExtra("url");
    }

    @Override
    public void onWebFinished(WebView view, String url) {

    }
}
