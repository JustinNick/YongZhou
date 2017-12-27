package com.citylinkdata.yongzhou.view.impl.activity;

import android.webkit.WebView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.config.Conts;
import com.citylinkdata.yongzhou.view.impl.activity.BaseWebActivity;

/**
 * Created by liqing on 2017/11/2.
 */

public class UserGuidanceActivity extends BaseWebActivity {
    @Override
    public String getWebTitle() {
        return getResources().getString(R.string.use_guidance);
    }

    @Override
    public boolean isShowloading() {
        return false;
    }

    @Override
    public String getUrl() {
        return Conts.APP_SYS_INFO + "?p=use_guide";
    }

    @Override
    public void onWebFinished(WebView view, String url) {

    }
}
