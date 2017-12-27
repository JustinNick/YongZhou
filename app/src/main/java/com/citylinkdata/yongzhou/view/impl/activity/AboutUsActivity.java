package com.citylinkdata.yongzhou.view.impl.activity;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.presenter.BasePresenter;

public class AboutUsActivity extends BaseAppActivity {

    private TextView mTvOfficialWeb;
    private TextView mTvVersion;
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        mTvOfficialWeb = (TextView) findViewById(R.id.tv_official_website);
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        mTvVersion.setText(getVersionName());
    }

    public void onClick(View v){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        String strUrl = "http://"+mTvOfficialWeb.getText().toString().trim();
        Uri content_url = Uri.parse(strUrl);
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 版本号
     * @return  当前应用的版本号
     */
    public String getVersionName() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            String version_name = info.versionName;
            return "v"+version_name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
