package com.citylinkdata.yongzhou.view.impl.activity;

import android.os.Bundle;

import com.citylinkdata.yongzhou.presenter.BasePresenter;
import com.citylinkdata.yongzhou.view.impl.iview.IBaseView;


public abstract class BaseActivity<T extends BasePresenter,V extends IBaseView> extends BaseAppActivity {
    protected T presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
//        presenter.attach((V)this);

        setContentView(getLayoutId());
        initView();
    }

    protected abstract T getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deAttach();
    }

//    protected void hideActionBar(){
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.hide();
//        }
//
//    }
}
