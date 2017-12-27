package com.citylinkdata.yongzhou.view.impl.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;


import com.citylinkdata.yongzhou.R;
import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.userview.TitleView;


public abstract class BaseWebActivity extends BaseAppActivity {
    public WebView mWebView;
    public TitleView titleView;
    private ProgressBar loading;


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @Override
    protected void initView() {

        titleView = (TitleView) findViewById(R.id.title_view);
        if (TextUtils.isEmpty(getWebTitle()))
            titleView.setVisibility(View.GONE);
        else
            titleView.setTitle(getWebTitle());
        mWebView = (WebView) findViewById(R.id.web_view);
        loading = (ProgressBar) findViewById(R.id.progressbar);
        WebSettings mWebSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            mWebSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebSettings.setLoadsImagesAutomatically(false);
        }

        //开启WebView对JS脚本的支持
        mWebSettings.setJavaScriptEnabled(true);

        //设置允许在http环境中访问https资源和服务
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //设置默认的网页编码
        mWebSettings.setDefaultTextEncodingName("utf-8");

        //设置JS是否可以打开WebView新窗口
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //设置WebView是否支持多窗口，为true,需要重写WebChromeClient#onCreateWindow函数
        mWebSettings.setSupportMultipleWindows(true);


        mWebView.loadUrl(getUrl());
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (isShowloading()) {
                    if (url.equals(getUrl()))
                        showLoading("加载中。。。");
                }

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                if (isShowloading()) {
                    closeLoading();
                }

//                loading.setVisibility(View.GONE);
                onWebFinished(view, url);
            }

        });

        //设置WebChromeClient类
        mWebView.setWebChromeClient(new WebChromeClient() {

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

//                    loading.setProgress(newProgress);

            }
        });


    }

    public abstract String getWebTitle();

    public abstract boolean isShowloading();

    public abstract String getUrl();

    public abstract void onWebFinished(WebView view, String url);

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }


}

